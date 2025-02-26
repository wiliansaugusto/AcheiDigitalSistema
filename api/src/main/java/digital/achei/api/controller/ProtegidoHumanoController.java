package digital.achei.api.controller;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import digital.achei.api.service.ProtegidoHumanoService;
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
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class ProtegidoHumanoController {

    private static final Logger log = LogManager.getLogger(ProtegidoHumanoController.class);
    @Autowired
    ProtegidoHumanoService protegidoHumanoService;


    @PostMapping("salvar-protegido-humano")
    ResponseEntity salvarProtegidoHumano(@Valid @RequestBody ProtegidoHumanoDTO protegidoHumano) {
        log.info("salvando protegido {}",protegidoHumano.getNomeProtegido());
        ProtegidoHumanoDTO result = protegidoHumanoService.salvarProtegidoHumano(protegidoHumano);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PutMapping("editar-protegido-humano")
    ResponseEntity editarProtegidoHumano(@Valid @RequestBody ProtegidoHumanoDTO protegidoHumano) {
        log.info("editando protegido {}",protegidoHumano.getNomeProtegido());
        ProtegidoHumanoDTO result = protegidoHumanoService.editarProtegidoHumano(protegidoHumano);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("pesquisar-protegido-humano/{id}")
    ResponseEntity pesquisarProtegidoPorID(@PathVariable Long id) {
        log.info("pesquisando o  protegido {}",id);
        ProtegidoHumanoResponse result = protegidoHumanoService.pesquisarProtegidoHumano(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("deletar-protegido-humano/{id}")
    ResponseEntity deletarProtegidoPorID(@PathVariable Long id) {

        Optional<ProtegidoHumanoDTO> result = protegidoHumanoService.deletarProtegido(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("listar-protegidos-por-usuario/{id}")
    ResponseEntity pesquisarProtegidosPorUsuarios(@PathVariable Long id){
        List<ProtegidoHumanoDTO> result = protegidoHumanoService.pesquisarProtegidoHumanoPorUsuario(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
