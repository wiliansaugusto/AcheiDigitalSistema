package digital.achei.api.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviaEmailRequest {
String nome;

@Email
String email;
String mensagem;

}

