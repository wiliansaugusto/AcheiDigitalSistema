package digital.achei.api.controller;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.request.EditarContatoRequest;
import digital.achei.api.service.ContatoService;
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
public class ContatoController {

    private static final Logger log = LogManager.getLogger(ContatoController.class);

    @Autowired
    ContatoService contatoService;

    @DeleteMapping("deletar-contato/{idContato}/{idUsuario}")
    ResponseEntity deletarContato(@PathVariable Long idContato, @PathVariable Long idUsuario)   {
        log.info("deletando o telefone");
        try{
            contatoService.deletarContato(idContato,idUsuario);
        }catch (BasicException e){
            throw new BasicException(e.getMessage(), e.getStatus());
        }
        return new ResponseEntity<>(new Utils().createSimpleJson("Contato deletado com sucesso"), HttpStatus.ACCEPTED);
    }

    @PostMapping("editar-contato")
    ResponseEntity editarContato(@Valid  @RequestBody EditarContatoRequest editarContatoRequest)   {
        log.info("Editando o telefone");
        ContatoDTO contato = null;
        try{
          contato =  contatoService.editarContato(editarContatoRequest.getContatoDTO(), editarContatoRequest.getIdUsuario());
        }catch (BasicException e){
            throw new BasicException(e.getMessage(), e.getStatus());
        }
        return new ResponseEntity<>(contato, HttpStatus.ACCEPTED);
    }


}
