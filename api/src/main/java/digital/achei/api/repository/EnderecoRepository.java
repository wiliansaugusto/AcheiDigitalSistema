package digital.achei.api.repository;

import digital.achei.api.DTO.EnderecoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoDTO, Long> {

    @Query("SELECT e FROM EnderecoDTO e WHERE e.usuario.idUsuario = :usuario")
    List<EnderecoDTO> findByIdUsuarioEndereco(@Param("usuario")Long usuario);

    @Modifying
    @Transactional
    @Query("DELETE FROM EnderecoDTO e WHERE e.idEndereco = :idEndereco")
    void deleteByIdEndereco(@Param("idEndereco") Long idEndereco) ;
}
