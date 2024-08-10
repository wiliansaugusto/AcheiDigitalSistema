package digital.achei.api.DTO;

import digital.achei.api.utils.validacaoCPF.ValidCPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    Long idlogin;

    @Email
    String email;

    @Pattern(regexp = "^\\d{11}$", message = "Campo CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @Size(min=8)
    @NotNull(message = "Campo senha nao pode ser nulo")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()!])[A-Za-z\\d@#$%^&*()!]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    String senha;

    @OneToOne
    UsuarioDTO usuario;
}


;




