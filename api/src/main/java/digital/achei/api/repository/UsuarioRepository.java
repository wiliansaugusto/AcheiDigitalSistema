package digital.achei.api.repository;

import digital.achei.api.DTO.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long> {
    @Query("SELECT e FROM UsuarioDTO e WHERE e.cpf = :cpf")
    Optional<UsuarioDTO> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT e FROM UsuarioDTO e WHERE e.cpf = :cpf AND e.senha = :senha")
    UsuarioDTO loginCPF(@Param("cpf") String cpf, @Param("senha") String senha);

    @Query("SELECT e FROM UsuarioDTO e WHERE e.email = :email AND e.senha = :senha")
    UsuarioDTO loginEmail(@Param("email") String email, @Param("senha") String senha);

    Optional<UsuarioDTO> findByEmail(String email);


}

