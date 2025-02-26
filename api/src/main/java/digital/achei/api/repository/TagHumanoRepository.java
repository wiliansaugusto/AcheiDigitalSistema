package digital.achei.api.repository;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagHumanoRepository extends JpaRepository<TagHumanoDTO, Long> {

    @Query("SELECT e FROM TagHumanoDTO e WHERE e.usuario.idUsuario = :idUsuario")
    List<TagHumanoDTO> findByIdUsuario(@Param("idUsuario") Long idUsuario);}
