package digital.achei.api.repository;

import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.DTO.TagPetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagPetRepository extends JpaRepository<TagPetDTO,Long> {
    @Query("SELECT e FROM TagPetDTO e WHERE e.usuario.idUsuario = :idUsuario")
    List<TagPetDTO> findByIdUsuario(@Param("idUsuario") Long idUsuario);}
