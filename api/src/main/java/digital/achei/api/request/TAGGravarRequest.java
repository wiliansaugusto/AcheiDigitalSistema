package digital.achei.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TAGGravarRequest {

    @NotNull(message = "IdTag deve ser informada")
    Long idTag;

    @NotNull(message = "Protegido  deve ser informado")
    Long idProtegido;
}
