package digital.achei.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TAGGravarPet {
    @NotNull(message = "IdTagPet deve ser informada")
    Long idTagPet;

    @NotNull(message = "Protegido Pet deve ser informado")
    Long idProtegidoPet;
}
