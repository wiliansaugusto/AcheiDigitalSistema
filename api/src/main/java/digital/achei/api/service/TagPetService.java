package digital.achei.api.service;

import digital.achei.api.DTO.TagPetDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.ContatoRepository;
import digital.achei.api.repository.EnderecoRepository;
import digital.achei.api.repository.TagPetRepository;
import digital.achei.api.request.TAGGravarPet;
import digital.achei.api.responses.ProtegidoPetResponse;
import digital.achei.api.responses.TagPetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class TagPetService {
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    TagPetRepository tagPetRepository;

    @Autowired
    ProtegidoPetService protegidoPetService;

    private static final Logger log = LogManager.getLogger(TagPetService.class);

    public TagPetResponse salvarProtegidoPet(TAGGravarPet tagGravarPet) {
        log.info("Pesquisando o Protegido com id: {}", tagGravarPet.getIdProtegidoPet());

        Optional<TagPetDTO> tag = tagPetRepository.findById(tagGravarPet.getIdTagPet());
        if (tag.isPresent() || tagGravarPet.getIdTagPet() == 0) {
            throw new BasicException("Tag id já gravada");
        }
        ProtegidoPetResponse protegido =
                protegidoPetService.pesquisarProtegidoPet(tagGravarPet.getIdProtegidoPet());

        TagPetDTO tagPetDTO = new TagPetDTO();
        tagPetDTO.setProtegidoPet(protegido.getProtegido());
        tagPetDTO.setUsuario(protegido.getProtegido().getUsuario());
        tagPetDTO.setIdTagPet(tagGravarPet.getIdTagPet());

        log.info("Salvando nova tag sendo gravada banco de dados" + tagPetDTO.getProtegidoPet());

        TagPetDTO result = tagPetRepository.save(tagPetDTO);
        TagPetResponse response = new TagPetResponse(result.getIdTagPet(), result.getProtegidoPet().getIdProtegidoPet(), result.getUsuario().getIdUsuario());

        return response;
    }

    public TagPetResponse editarTAGPet(TAGGravarPet tagGravarPet) {
        Optional<TagPetDTO> result = tagPetRepository.findById(tagGravarPet.getIdTagPet());

        if (result.isEmpty()) {
            throw new BasicException("Tag nao encontrada");
        }
        ProtegidoPetResponse protegido = protegidoPetService.pesquisarProtegidoPet(tagGravarPet.getIdProtegidoPet());
        if (!result.get().getUsuario().getIdUsuario().equals(protegido.getProtegido().getUsuario().getIdUsuario())) {
            throw new BasicException("A tag não pertence aos usuario");
        }
        result.get().setIdTagPet(tagGravarPet.getIdTagPet());
        result.get().setProtegidoPet(protegido.getProtegido());
        TagPetDTO resultSave = tagPetRepository.save(result.get());
        TagPetResponse response = new TagPetResponse(resultSave.getIdTagPet(), resultSave.getProtegidoPet().getIdProtegidoPet(), resultSave.getUsuario().getIdUsuario());

        return response;

    }

    public ProtegidoPetResponse listarDadosTagsPet(Long tagID) {
        Optional<TagPetDTO> resultTag = tagPetRepository.findById(tagID);
        if (resultTag.isEmpty()) {
            throw new BasicException("TAg não encontrado", HttpStatus.BAD_REQUEST);
        }
        ProtegidoPetResponse protegido = protegidoPetService.pesquisarProtegidoPet(resultTag.get().getProtegidoPet().getIdProtegidoPet());
        return protegido;
    }

    public List listarTags(Long id){
        List<TagPetDTO> result = tagPetRepository.findByIdUsuario(id);
        if(result.isEmpty()){
            throw new BasicException("Usuario nao possuem tags");
        }
        List retorno = new ArrayList();
        result.stream().forEach(r -> {
            Map<String, Object> adicionar = new HashMap<>();
            adicionar.put("idTag",r.getIdTagPet());
            adicionar.put("idProtegido", r.getProtegidoPet().getIdProtegidoPet());
            adicionar.put("nomeProtegido",r.getProtegidoPet().getNomeProtegidoPet());
            retorno.add(adicionar);
        });
        return retorno;
    }


    public TagPetDTO liberarTag(Long id){
        Optional<TagPetDTO> result = tagPetRepository.findById(id);
        if(result.isEmpty()){
            throw new BasicException("Tag nao encontrada");
        }
        tagPetRepository.delete(result.get());
        return new TagPetDTO();
    }
}
