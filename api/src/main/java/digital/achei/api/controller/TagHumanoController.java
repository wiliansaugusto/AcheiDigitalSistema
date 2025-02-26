package digital.achei.api.controller;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.request.TAGGravarRequest;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import digital.achei.api.responses.TagHumanoResponse;
import digital.achei.api.service.TagHumanoService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class TagHumanoController {

    private static final Logger log = LogManager.getLogger(TagHumanoController.class);

    @Autowired
    TagHumanoService tagHumanoService;

    @PostMapping("gravar-tag")
    ResponseEntity salvarProtegidoHumano(@Valid @RequestBody TAGGravarRequest tagGravarRequest) {
        log.info("gravando  tag {}", tagGravarRequest.getIdProtegido());
        TagHumanoResponse result = tagHumanoService.salvarProtegidoTag(tagGravarRequest);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PostMapping("editar-tag")
    ResponseEntity editarProtegido(@Valid @RequestBody TAGGravarRequest tagGravarRequest){
        log.info("editando a tag: {}" ,tagGravarRequest.getIdTag());
        TagHumanoResponse result = tagHumanoService.editarTAG(tagGravarRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("liberar-tag/{id}")
    ResponseEntity liberarTag(@Valid @PathVariable Long id) {
        log.info("gravando id na tag {}", id);
        TagHumanoDTO result = tagHumanoService.liberarTag(id);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


    @GetMapping("listar-tags/{id}")
    ResponseEntity listarTags(@Valid @PathVariable Long id) {
        log.info("Buscando tags para o idUsuario {}", id);
        List result = tagHumanoService.listarTags(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("tag/{tagID}")
    ResponseEntity listarDadosTAg(@PathVariable Long tagID){
        log.info("Buscando tags para o idUsuario {}", tagID);
        ProtegidoHumanoResponse result = tagHumanoService.listarDadosTags(tagID);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
