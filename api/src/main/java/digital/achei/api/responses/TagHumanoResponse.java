package digital.achei.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagHumanoResponse {

    Long idTag;
    Long protegido;
    Long usuario;

}
