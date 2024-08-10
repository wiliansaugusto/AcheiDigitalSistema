package digital.achei.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioDeSenhaRequest {
    @NotBlank(message = "Email nao pode ser em branco")
    @Email(message = "O email deve ter um padrao valido")
    String email;
}
