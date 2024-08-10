package digital.achei.api.repository;

import digital.achei.api.DTO.ContatoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContatoRepository extends JpaRepository<ContatoDTO, Long> {
    @Query("SELECT e FROM ContatoDTO e WHERE e.usuario.idUsuario = :usuario")
    List<ContatoDTO> findByIdUsuario(@Param("usuario")Long usuario);

}
