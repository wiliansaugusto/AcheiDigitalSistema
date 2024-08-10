package digital.achei.api.controller;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.responses.TagHumanoResponse;
import digital.achei.api.service.TagHumanoService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.105:4200"})
public class TagHumanoController {

    private static final Logger log = LogManager.getLogger(TagHumanoController.class);

    @Autowired
    TagHumanoService tagHumanoService;

    @PostMapping("gravar-tag/{id}")
    ResponseEntity salvarProtegidoHumano(@Valid @PathVariable Long id) {
        log.info("gravando id na tag {}", id);
        TagHumanoResponse result = tagHumanoService.salvarProtegidoTag(id);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PostMapping("editar-tag")
    ResponseEntity editarProtegido(@Valid @RequestBody TagHumanoDTO tagHumanoDTO){
        log.info("editando a tag: {}" ,tagHumanoDTO.getIdTag());
        TagHumanoResponse result = tagHumanoService.editarTAG(tagHumanoDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PostMapping("liberar-tag/{id}")
    ResponseEntity liberarTaag(@Valid @PathVariable Long id) {
        log.info("gravando id na tag {}", id);
        TagHumanoDTO result = tagHumanoService.liberarTag(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
