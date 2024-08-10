package digital.achei.api.repository;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProtegidoHumanoRepository extends JpaRepository<ProtegidoHumanoDTO, Long> {

    @Query("SELECT e FROM ProtegidoHumanoDTO e WHERE e.usuario.idUsuario = :usuario")
    List<ProtegidoHumanoDTO> findByIdUsuario(@Param("usuario")Long usuario);


}
