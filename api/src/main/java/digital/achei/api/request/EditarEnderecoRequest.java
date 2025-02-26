package digital.achei.api.request;

import digital.achei.api.DTO.EnderecoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarEnderecoRequest {
    EnderecoDTO endereco;
    Long idUsuario;
}
