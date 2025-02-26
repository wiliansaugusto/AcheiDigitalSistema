package digital.achei.api.controller;

import digital.achei.api.DTO.TagPetDTO;
import digital.achei.api.request.TAGGravarPet;
import digital.achei.api.request.TAGGravarRequest;
import digital.achei.api.responses.ProtegidoPetResponse;
import digital.achei.api.responses.TagPetResponse;
import digital.achei.api.service.TagPetService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class TagPetController {
    private static final Logger log = LogManager.getLogger(TagPetController.class);

    @Autowired
    TagPetService tagPetService;
    @PostMapping("gravar-tag-pet")
    ResponseEntity salvarProtegidoPet(@Valid @RequestBody TAGGravarPet tagGravarRequest) {
        log.info("gravando  tag {}", tagGravarRequest.getIdProtegidoPet());
        TagPetResponse result = tagPetService.salvarProtegidoPet(tagGravarRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @PostMapping("editar-tag-pet")
    ResponseEntity editarProtegidoPet(@Valid @RequestBody TAGGravarPet tagGravarRequest){
        log.info("editando a tag: {}" ,tagGravarRequest.getIdTagPet());
        TagPetResponse result = tagPetService.editarTAGPet(tagGravarRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("liberar-tag/{id}")
    ResponseEntity liberarTagPet(@Valid @PathVariable Long id) {
        log.info("gravando id na tag {}", id);
        TagPetDTO result = tagPetService.liberarTag(id);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


    @GetMapping("listar-tags/{id}")
    ResponseEntity listarTagsPet(@Valid @PathVariable Long id) {
        log.info("Buscando tags para o idUsuario {}", id);
        List result = tagPetService.listarTags(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("tag/{tagID}")
    ResponseEntity listarDadosTagPet(@PathVariable Long tagID){
        log.info("Buscando tags para o idUsuario {}", tagID);
        ProtegidoPetResponse result = tagPetService.listarDadosTagsPet(tagID);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }



}
