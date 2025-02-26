package digital.achei.api.request;

import digital.achei.api.DTO.ContatoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarContatoRequest {

    ContatoDTO contatoDTO;
    Long idUsuario;

}
