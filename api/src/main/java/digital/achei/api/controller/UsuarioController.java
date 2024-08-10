package digital.achei.api.controller;

import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.responses.UsarioProtegidos;
import digital.achei.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.105:4200"})
public class UsuarioController {
    private static final Logger log = LogManager.getLogger(UsuarioController.class);

    @Autowired
    UsuarioService usuarioService;
    @PostMapping("usuario-novo")
    public ResponseEntity salvarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO)  {
        Optional<UsuarioDTO> resposta = usuarioService.salvarUsuario(usuarioDTO);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping("usuario-editar")
    public ResponseEntity editarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO)  {
        Optional<UsuarioDTO> resposta = usuarioService.editarUsuario(usuarioDTO);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    @GetMapping("usuario-editar-status/{id}")
    public ResponseEntity alterarStatusUsuario( @PathVariable Long id)  {
        Optional<UsuarioDTO> resposta = usuarioService.alterarStatusUsuario(id);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    @GetMapping("usuario-listar-todos")
    public ResponseEntity listarTodosUsuario()  {
        List<UsuarioDTO> resposta = usuarioService.listarTodosUsuarios();
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    @GetMapping("usuario-listar-cpf/{cpf}")
    public ResponseEntity listarUsuarioCPF(@PathVariable String cpf)  {
        Optional<UsuarioDTO> resposta = usuarioService.listarUsuarioCpf(cpf);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    @GetMapping("usuario-listar-id/{id}")
    public ResponseEntity listarUsuarioID(@PathVariable Long id)  {
        Optional<UsuarioDTO> resposta = usuarioService.listarUsuarioId(id);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
    @GetMapping("usuario-listar-protegido/{id}")
    public ResponseEntity listarUsuarioPOrotegidoID(@PathVariable Long id)  {
        UsarioProtegidos resposta = usuarioService.listarUsuarioProtegido(id);
        return  new ResponseEntity<>(resposta, HttpStatus.OK);
    }
}
