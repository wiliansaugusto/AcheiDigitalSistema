package digital.achei.api.controller;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.ProtegidoPetDTO;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import digital.achei.api.responses.ProtegidoPetResponse;
import digital.achei.api.service.ProtegidoHumanoService;
import digital.achei.api.service.ProtegidoPetService;
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
@RequestMapping("/api/pet")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class ProtegidoPetController {
    private static final Logger log = LogManager.getLogger(ProtegidoPetController.class);
    @Autowired
    ProtegidoPetService protegidoPetService;

    @PostMapping("salvar-protegido-pet")
    ResponseEntity salvarProtegidoPet(@Valid @RequestBody ProtegidoPetDTO protegidoPet) {
        log.info("salvando protegido {}",protegidoPet.getNomeProtegidoPet());
        ProtegidoPetDTO result = protegidoPetService.salvarProtegidoPet(protegidoPet);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("editar-protegido-pet")
    ResponseEntity editarProtegidoHumano(@Valid @RequestBody ProtegidoPetDTO protegidoPet) {
        log.info("editando protegido {}",protegidoPet.getNomeProtegidoPet());
        ProtegidoPetDTO result = protegidoPetService.editarProtegidoPet(protegidoPet);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @GetMapping("pesquisar-protegido-pet/{id}")
    ResponseEntity pesquisarProtegidoPorID(@PathVariable Long id) {
        log.info("pesquisando o  protegido {}",id);
        ProtegidoPetResponse result = protegidoPetService.pesquisarProtegidoPet(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("listar-protegidos-pet-por-usuario/{id}")
    ResponseEntity pesquisarProtegidosPorUsuarios(@PathVariable Long id){
        List<ProtegidoPetDTO> result = protegidoPetService.pesquisarProtegidoPetPorUsuario(id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @DeleteMapping("deletar-protegido-pet/{id}")
    ResponseEntity deletarProtegidoPorID(@PathVariable Long id) {
        Optional<ProtegidoPetDTO> result = protegidoPetService.deletarProtegidoPet(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
