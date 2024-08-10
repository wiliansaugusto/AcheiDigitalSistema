package digital.achei.api.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import digital.achei.api.utils.validacaoCPF.ValidCPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UsuarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    Long idUsuario;

    @Column(name = "nomeCompleto")
    @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres")
    @NotBlank(message = "O nome completo não pode estar vazio")
    String nomeCompleto;

    @Pattern(regexp = "^\\d{11}$", message = "Campo CPF deve conter exatamente 11 dígitos numéricos")
    @ValidCPF
    String cpf;

    @Email(message = "O campo EMAIL deve ser um endereço de e-mail válido")
    String email;

    @Size(min = 8)
    @NotNull(message = "Campo senha nao pode ser nulo")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()!])[A-Za-z\\d@#$%^&*()!]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    String senha;

    @Column(name = "isAtivo")
    boolean isAtivo = true;


    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate dataNascimento;

    @Column(name = "dataCriacao", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date dataCriacao = new Date();

    @Column(name = "dataAlteracao")
    @Temporal(TemporalType.TIMESTAMP)
    Date dataAlteracao = new Date();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<ContatoDTO> contato = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<EnderecoDTO> endereco = new ArrayList<>();
}
