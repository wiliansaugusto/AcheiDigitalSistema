package digital.achei.api.repository;

import digital.achei.api.DTO.EnderecoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<EnderecoDTO, Long> {

    @Query("SELECT e FROM EnderecoDTO e WHERE e.usuario.idUsuario = :usuario")
    List<EnderecoDTO> findByIdUsuarioEndereco(@Param("usuario")Long usuario);
}
