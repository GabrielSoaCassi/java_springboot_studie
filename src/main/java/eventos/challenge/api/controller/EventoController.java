package eventos.challenge.api.controller;

import eventos.challenge.api.models.Evento;
import eventos.challenge.api.models.RedeSocial;
import eventos.challenge.api.models.dto.EventoDTO;
import eventos.challenge.api.models.dto.RedeSocialDTO;
import eventos.challenge.api.repositories.EventoRepository;
import eventos.challenge.api.repositories.RedeSocialRepository;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventoController {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    RedeSocialRepository redeSocialRepository;
    @GetMapping("/evento")
    public List<Evento> obterEventos(){
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

    @GetMapping("/evento/{id}")
    public Evento obterEventoPorId(@PathVariable Long id){
        List<RedeSocial> redesSociais = redeSocialRepository.findRedeSociaisByEventoId(id);
        Evento evento = eventoRepository.findById(id).orElse(null);
        evento.setRedesSociais(redesSociais);
        return evento;
    }


    @PostMapping("/evento")
    public Evento cadastrarEvento(@RequestBody EventoDTO eventoDTO){
        Evento evento = new Evento(eventoDTO);
        eventoRepository.saveAndFlush(evento);
        ArrayList<RedeSocial>  redesSociais = new ArrayList<RedeSocial>();
        for(RedeSocialDTO redeSocialDTO : eventoDTO.redesSociais()){
            redeSocialRepository.saveAndFlush(new RedeSocial(redeSocialDTO,evento));
        }

        return evento;
    }

    @PutMapping("/evento/{id}")
    public boolean atualizarEvento(@RequestBody EventoDTO evento,@PathVariable Long id) {
        boolean succes = false;
        try{
            List<RedeSocial> redesSociaisExistente = redeSocialRepository.findRedeSociaisByEventoId(id);
            Evento eventoExistente = eventoRepository.findById(id).orElse(null);
            eventoExistente.atualizarEvento(evento);
        if(evento.redesSociais().size() > 0){
            for (RedeSocialDTO redeSocialAtualizada : evento.redesSociais()) {
                for (RedeSocial redeSocialExistente : redesSociaisExistente) {
                    if (redeSocialExistente.getId().equals(redeSocialAtualizada.id())) {
                        redeSocialExistente.atualizaRedeSocial(redeSocialAtualizada);
                        break;
                    }
                }
            }

        }
        eventoRepository.saveAndFlush(eventoExistente);
        redeSocialRepository.saveAllAndFlush(redesSociaisExistente);
         succes = true;
        }
        catch (Exception e){
            e.printStackTrace();
            succes = false;
        }
        finally {
            return succes;
        }
    }

    @DeleteMapping("/evento/{id}")
    public boolean deletarEvento(@PathVariable Long id){
        boolean succes = false;
        try{
            eventoRepository.deleteById(id);
            succes = true;
        }catch(Exception e){
            e.printStackTrace();
            succes = false;
        }finally {
            return succes;
        }
    }

}
