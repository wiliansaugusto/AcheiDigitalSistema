package digital.achei.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.List;

@Getter
@Setter
public class EnvioEmailGenericoRequest {
    String messagem;
    List<String> email;
    String pdf;


}
