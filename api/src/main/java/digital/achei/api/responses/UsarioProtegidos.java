package digital.achei.api.responses;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsarioProtegidos {

UsuarioDTO usuario;
List<ProtegidoHumanoDTO> protegidos;
}
