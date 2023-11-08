package eventos.challenge.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eventos.challenge.api.models.dto.EventoDTO;
import eventos.challenge.api.models.dto.RedeSocialDTO;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="redes_sociais")
public class RedeSocial implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String titulo;
    public String link;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonBackReference
    public Evento evento;

    public RedeSocial(){}
    public RedeSocial(RedeSocialDTO redeSocial, Evento evento) {
        this.titulo = redeSocial.titulo();
        this.link = redeSocial.link();
        this.evento = evento;
    }

    public void atualizaRedeSocial(RedeSocialDTO redeSocial) {
        if (!redeSocial.titulo().isEmpty() || redeSocial.titulo().isEmpty())
            this.titulo = redeSocial.titulo();
        if (!redeSocial.link().isEmpty() || redeSocial.link().isEmpty())
            this.link = redeSocial.link();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
