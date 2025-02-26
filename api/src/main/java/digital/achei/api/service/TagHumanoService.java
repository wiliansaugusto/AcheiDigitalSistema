package digital.achei.api.service;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.TagHumanoRepository;
import digital.achei.api.request.TAGGravarRequest;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import digital.achei.api.responses.TagHumanoResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagHumanoService {
    private static final Logger log = LogManager.getLogger(TagHumanoService.class);

    @Autowired
    ProtegidoHumanoService  protegidoHumanoService;

    @Autowired
    TagHumanoRepository tagHumanoRepository;


    public TagHumanoResponse salvarProtegidoTag(TAGGravarRequest tagGravarRequest){
        log.info("Pesquisando o Protegido com id: {}", tagGravarRequest.getIdProtegido());

        Optional<TagHumanoDTO> tag = tagHumanoRepository.findById(tagGravarRequest.getIdTag());
        if(tag.isPresent() || tagGravarRequest.getIdTag() == 0){
            throw new BasicException("Tag id já gravada");
        }
        ProtegidoHumanoResponse protegido = protegidoHumanoService.pesquisarProtegidoHumano(tagGravarRequest.getIdProtegido());

        TagHumanoDTO tagHumanoDTO = new TagHumanoDTO();
        tagHumanoDTO.setProtegido(protegido.getProtegido());
        tagHumanoDTO.setUsuario(protegido.getProtegido().getUsuario());
        tagHumanoDTO.setIdTag(tagGravarRequest.getIdTag());

        log.info("Salvando nova tag sendo gravada banco de dados"+tagHumanoDTO.getIdTag());

        TagHumanoDTO result = tagHumanoRepository.save(tagHumanoDTO);
        TagHumanoResponse response = new TagHumanoResponse(result.getIdTag(), result.getProtegido().getIdProtegidoHumano(),result.getUsuario().getIdUsuario());

        return response;
    }

    public TagHumanoResponse editarTAG(TAGGravarRequest tagGravarRequest) {
        Optional<TagHumanoDTO> result = tagHumanoRepository.findById(tagGravarRequest.getIdTag());

        if(result.isEmpty()){
            throw new BasicException("Tag nao encontrada");
        }
        ProtegidoHumanoResponse protegido = protegidoHumanoService.pesquisarProtegidoHumano(tagGravarRequest.getIdProtegido());
        if(!result.get().getUsuario().getIdUsuario().equals(protegido.getProtegido().getUsuario().getIdUsuario())){
            throw new BasicException("A tag não pertence aos usuario");
        }
        result.get().setIdTag(tagGravarRequest.getIdTag());
        result.get().setProtegido(protegido.getProtegido());
        TagHumanoDTO resultSave = tagHumanoRepository.save(result.get());
        TagHumanoResponse response = new TagHumanoResponse(resultSave.getIdTag(), resultSave.getProtegido().getIdProtegidoHumano(),resultSave.getUsuario().getIdUsuario());

        return response;

    }

    public TagHumanoDTO liberarTag(Long id){
        Optional<TagHumanoDTO> result = tagHumanoRepository.findById(id);
        if(result.isEmpty()){
            throw new BasicException("Tag nao encontrada");
        }
         tagHumanoRepository.delete(result.get());
         return new TagHumanoDTO();
    }

    public List listarTags(Long id){
        List<TagHumanoDTO> result = tagHumanoRepository.findByIdUsuario(id);
        if(result.isEmpty()){
            throw new BasicException("Usuario nao possuem tags");
        }
        List retorno = new ArrayList();
        result.stream().forEach(r -> {
            Map<String, Object> adicionar = new HashMap<>();
            adicionar.put("idTag",r.getIdTag());
            adicionar.put("idProtegido", r.getProtegido().getIdProtegidoHumano());
            adicionar.put("nomeProtegido",r.getProtegido().getNomeProtegido());
            retorno.add(adicionar);
        });
        return retorno;
    }

    public ProtegidoHumanoResponse listarDadosTags(Long tagID) {
        Optional<TagHumanoDTO> resultTag = tagHumanoRepository.findById(tagID);
        if (resultTag.isEmpty()){
            throw new BasicException("TAg não encontrado", HttpStatus.BAD_REQUEST);
        }
        ProtegidoHumanoResponse protegido = protegidoHumanoService.pesquisarProtegidoHumano(resultTag.get().getProtegido().getIdProtegidoHumano());
        return protegido ;
    }
}
