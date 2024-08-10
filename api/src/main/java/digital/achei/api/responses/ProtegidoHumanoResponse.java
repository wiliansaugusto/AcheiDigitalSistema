package digital.achei.api.responses;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.DTO.ProtegidoHumanoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProtegidoHumanoResponse {

    ProtegidoHumanoDTO protegido;
    List<ContatoDTO> contato;
    List<EnderecoDTO> endereco;

}
