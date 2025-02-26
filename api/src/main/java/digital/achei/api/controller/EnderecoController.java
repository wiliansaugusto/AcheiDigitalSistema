package digital.achei.api.controller;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.request.EditarContatoRequest;
import digital.achei.api.request.EditarEnderecoRequest;
import digital.achei.api.service.ContatoService;
import digital.achei.api.service.EnderecoService;
import digital.achei.api.utils.Utils;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class EnderecoController {
    private static final Logger log = LogManager.getLogger(EnderecoController.class);

    @Autowired
    EnderecoService enderecoService;

    @DeleteMapping("deletar-endereco/{idEndereco}/{idUsuario}")
    ResponseEntity deletarEndereco(@PathVariable Long idEndereco, @PathVariable Long idUsuario)   {
        log.info("deletando o enderecp");
        try{
            enderecoService.deletarEndereco(idEndereco,idUsuario);
        }catch (BasicException e){
            throw new BasicException(e.getMessage(), e.getStatus());
        }
        return new ResponseEntity<>(new Utils().createSimpleJson("Endereco deletado com sucesso"), HttpStatus.ACCEPTED);
    }

    @PostMapping("editar-endereco")
    ResponseEntity editarEndereco(@Valid @RequestBody EditarEnderecoRequest editarEnderecoRequest)   {
        log.info("Editando o endereco");
        EnderecoDTO endereco = null;
        try{
            endereco =  enderecoService.editarEndereco(editarEnderecoRequest.getEndereco(), editarEnderecoRequest.getIdUsuario());
        }catch (BasicException e){
            throw new BasicException(e.getMessage(), e.getStatus());
        }
        return new ResponseEntity<>(endereco, HttpStatus.ACCEPTED);
    }
}
