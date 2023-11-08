package eventos.challenge.api.repositories;

import eventos.challenge.api.models.RedeSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedeSocialRepository extends JpaRepository<RedeSocial,Long> {
    @Query("SELECT rs FROM RedeSocial rs JOIN FETCH rs.evento e WHERE e.id = :eventoId")
    List<RedeSocial> findRedeSociaisByEventoId(Long eventoId);

}
