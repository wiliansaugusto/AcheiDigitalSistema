package digital.achei.api.repository;

import digital.achei.api.DTO.ContatoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContatoRepository extends JpaRepository<ContatoDTO, Long> {
    @Query("SELECT e FROM ContatoDTO e WHERE e.usuario.idUsuario = :usuario")
    List<ContatoDTO> findByIdUsuario(@Param("usuario") Long usuario);
    @Modifying
    @Transactional
    @Query("DELETE FROM ContatoDTO e WHERE e.idContato = :idContato")
    void deleteByIdContato(@Param("idContato") Long idContato) ;

}
