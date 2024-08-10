package digital.achei.api.service;

import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.TagHumanoRepository;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import digital.achei.api.responses.TagHumanoResponse;
import digital.achei.api.responses.UsarioProtegidos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagHumanoService {
    private static final Logger log = LogManager.getLogger(TagHumanoService.class);

    @Autowired
    ProtegidoHumanoService  protegidoHumanoService;

    @Autowired
    TagHumanoRepository tagHumanoRepository;

    @Autowired
    UsuarioService usuarioService;

    public TagHumanoResponse salvarProtegidoTag(Long id){
        log.info("Pesquisando o Protegido com id: {}", id);
        ProtegidoHumanoResponse protegido = protegidoHumanoService.pesquisarProtegidoHumano(id);
        TagHumanoDTO tagHumanoDTO = new TagHumanoDTO();
        tagHumanoDTO.setProtegido(protegido.getProtegido());
        tagHumanoDTO.setUsuario(protegido.getProtegido().getUsuario());

        log.info("Salvando nova tag sendo gravada banco de dados");

        TagHumanoDTO result = tagHumanoRepository.save(tagHumanoDTO);
        TagHumanoResponse response = new TagHumanoResponse(result.getIdTag(), result.getProtegido().getIdProtegidoHumano(),result.getUsuario().getIdUsuario());

        return response;
    }

    public TagHumanoResponse editarTAG(TagHumanoDTO tagHumanoDTO) {
        Optional<TagHumanoDTO> result = tagHumanoRepository.findById(tagHumanoDTO.getIdTag());
        if(result.isEmpty()){
            throw new BasicException("Tag nao encontrada");
        }

        UsarioProtegidos testeProtegidoHumano = usuarioService.listarUsuarioProtegido(tagHumanoDTO.getUsuario().getIdUsuario());
        List<ProtegidoHumanoDTO> teste = testeProtegidoHumano.getProtegidos().stream()
                .filter(idProtegido -> idProtegido.getIdProtegidoHumano().equals(tagHumanoDTO.getProtegido().getIdProtegidoHumano())).collect(Collectors.toList());
        if (teste.isEmpty()){
            throw new BasicException("usuario e protegido não são compativeis");
        }

        result.get().setUsuario(tagHumanoDTO.getUsuario());
        result.get().setProtegido(tagHumanoDTO.getProtegido());
        TagHumanoDTO resultSave = tagHumanoRepository.save(result.get());
        TagHumanoResponse response = new TagHumanoResponse(resultSave.getIdTag(), resultSave.getProtegido().getIdProtegidoHumano(),resultSave.getUsuario().getIdUsuario());

        return response;

    }


    public TagHumanoDTO liberarTag(Long id){
        Optional<TagHumanoDTO> result = tagHumanoRepository.findById(id);
        if(result.isEmpty()){
            throw new BasicException("Tag nao encontrada");
        }
        result.get().setProtegido(null);
        result.get().setUsuario(null);
        return tagHumanoRepository.save(result.get());

    }
}
