package digital.achei.api.repository;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.ProtegidoPetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtegidoPetRepository extends JpaRepository<ProtegidoPetDTO, Long> {
    @Query("SELECT e FROM ProtegidoPetDTO e WHERE e.usuario.idUsuario = :usuario")
    List<ProtegidoPetDTO> findByIdUsuario(@Param("usuario")Long usuario);

}
