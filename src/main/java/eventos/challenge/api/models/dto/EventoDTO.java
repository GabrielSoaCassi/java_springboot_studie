package eventos.challenge.api.models.dto;


import eventos.challenge.api.models.RedeSocial;

import java.util.List;

public record EventoDTO(String titulo, String data, String local, List<RedeSocialDTO> redesSociais, String site,String horario) {}


