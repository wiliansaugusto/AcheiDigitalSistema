package digital.achei.api.responses;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.DTO.ProtegidoPetDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProtegidoPetResponse {
    ProtegidoPetDTO protegido;
    List<ContatoDTO> contato;
    List<EnderecoDTO> endereco;
    String email;
    String nomeUsuario;
}
