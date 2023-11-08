package eventos.challenge.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import eventos.challenge.api.models.dto.EventoDTO;

import eventos.challenge.api.models.dto.RedeSocialDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Entity
@Table(name = "eventos")
public class Evento  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Date data;
    private String local;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "evento",cascade = {CascadeType.ALL})
    @JsonManagedReference // Anotação para ajudar na serialização JSON
    private List<RedeSocial> redesSociais;
    private String site;
    private String horario;

    public Evento(){}
    public Evento (EventoDTO eventoDTO){
        this.titulo = eventoDTO.titulo();
        this.data = parseData(eventoDTO.data());
        this.local = eventoDTO.local();
        this.site = eventoDTO.site();
        this.horario = eventoDTO.horario();
    }
    public Date parseData(String dataStr) {
        Date data = null;
        String[] formatos = {"yyyy-MM-dd", "dd-MM-yyyy", "MM/dd/yyyy", "dd/MM/yyyy"};

        for (String formato : formatos) {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            sdf.setLenient(false);

            try {
                data = sdf.parse(dataStr);
                if (data != null) {
                    return data;
                }
            } catch (ParseException e) {
            }
        }
        String regexPattern = "\\d{4}-\\d{2}-\\d{2}|\\d{2}-\\d{2}-\\d{4}|\\d{2}/\\d{2}/\\d{4}|\\d{2}/\\d{2}/\\d{2}";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(dataStr);

        if (matcher.find()) {
            String dataFormatada = matcher.group();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                data = sdf.parse(dataFormatada);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setRedesSociais(List<RedeSocial> redesSociais) {
        this.redesSociais = redesSociais;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void atualizarEvento(EventoDTO eventoDTO) {
        if (eventoDTO.titulo() != null) {
            this.titulo = eventoDTO.titulo();
        }

        if (eventoDTO.data() != null) {
            this.data = parseData(eventoDTO.data());
        }

        if (eventoDTO.local() != null) {
            this.local = eventoDTO.local();
        }

        if (eventoDTO.site() != null) {
            this.site = eventoDTO.site();
        }

        if (eventoDTO.horario() != null) {
            this.horario = eventoDTO.horario();
        }
    }
}
