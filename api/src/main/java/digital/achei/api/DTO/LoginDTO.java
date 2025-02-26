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

    @Size(min=8, message = "deve conter no minimo 8 caracteres")
    @NotNull(message = "Campo senha nao pode ser nulo")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()!])[A-Za-z\\d@#$%^&*()!]{8,}$",
            message = "A senha está com problemas de segurança.")
    String senha;

    @OneToOne
    UsuarioDTO usuario;
}


;




