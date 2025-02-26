package digital.achei.api.service;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.TagHumanoDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.ContatoRepository;
import digital.achei.api.repository.EnderecoRepository;
import digital.achei.api.repository.ProtegidoHumanoRepository;
import digital.achei.api.repository.TagHumanoRepository;
import digital.achei.api.responses.ProtegidoHumanoResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ProtegidoHumanoService {
    @Autowired
    ProtegidoHumanoRepository protegidoHumanoRepository;
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    TagHumanoRepository tagHumanoRepository;

    private static final Logger log = LogManager.getLogger(ProtegidoHumanoService.class);

    public ProtegidoHumanoDTO salvarProtegidoHumano(ProtegidoHumanoDTO protegidoHumanoDTO) {
        if (protegidoHumanoDTO.getUsuario().getIdUsuario().equals(null)){
            throw new BasicException("Id do Usuario nao foi informado");
        }
        ProtegidoHumanoDTO result = protegidoHumanoRepository.save(protegidoHumanoDTO);
        return result;
    }

    public ProtegidoHumanoDTO editarProtegidoHumano(ProtegidoHumanoDTO protegidoHumanoDTO) {

        if (isNull(protegidoHumanoDTO.getIdProtegidoHumano())) {
            throw new BasicException("Id do protegido nao foi informado");
        }
        Optional<ProtegidoHumanoDTO> protegidoBusca = protegidoHumanoRepository.findById(protegidoHumanoDTO.getIdProtegidoHumano());
        if (protegidoBusca.isEmpty()) {
            throw new BasicException("Id do protegido nao foi encontrado na base de dados");

        }
        if (!protegidoBusca.get().getUsuario().getIdUsuario().equals(protegidoHumanoDTO.getUsuario().getIdUsuario())){
            throw new BasicException("Usuário diferente do cadastrado no banco");
        }

            return protegidoHumanoRepository.save(protegidoHumanoDTO);
    }
    public ProtegidoHumanoResponse pesquisarProtegidoHumano(Long id){
        Optional<ProtegidoHumanoDTO> protegidoHumanoDTO = protegidoHumanoRepository.findById(id);

        if (protegidoHumanoDTO.isEmpty()){
            throw new BasicException("Protegido com o id não encontrado", HttpStatus.BAD_REQUEST);
        }

        List<ContatoDTO> contatos = contatoRepository.findByIdUsuario(protegidoHumanoDTO.get().getUsuario().getIdUsuario());
        List<EnderecoDTO> enderecos = enderecoRepository.findByIdUsuarioEndereco(protegidoHumanoDTO.get().getUsuario().getIdUsuario());
        ProtegidoHumanoResponse response = new ProtegidoHumanoResponse();
        response.setProtegido(protegidoHumanoDTO.get());
        response.setContato(contatos);
        response.setEndereco(enderecos);
        response.setEmail(protegidoHumanoDTO.get().getUsuario().getEmail());
        response.setNomeUsuario(protegidoHumanoDTO.get().getUsuario().getNomeCompleto());
        return response;
    }
    public Optional<ProtegidoHumanoDTO> deletarProtegido(Long id){
        log.info("Procurando o usuario com ID: {}", id);
        Optional<ProtegidoHumanoDTO> protegido = protegidoHumanoRepository.findById(id);
        if (protegido.isEmpty()){
            throw new BasicException("Usuario não encontrado na base de dados");
        }
        List<TagHumanoDTO> tagProtegdos = tagHumanoRepository.findByIdUsuario(protegido.get().getUsuario().getIdUsuario());
        List<TagHumanoDTO> tagProtegdosDEl = tagProtegdos.stream().filter(r -> r.getProtegido().getIdProtegidoHumano().equals(id)).toList();
        log.info("O usuario encontrado: {} com id {} será deletado", protegido.get().getNomeProtegido(), protegido.get().getIdProtegidoHumano());
        for (TagHumanoDTO tag : tagProtegdosDEl){
            log.info(tag.getIdTag());
            tagHumanoRepository.delete(tag);
        }
        protegidoHumanoRepository.delete(protegido.get());
        log.info("Deletado com success");
        return protegidoHumanoRepository.findById(protegido.get().getIdProtegidoHumano());
    }
    public List<ProtegidoHumanoDTO> pesquisarProtegidoHumanoPorUsuario(Long id){
        log.info("Procurando o usuario com ID: {}", id);
        List<ProtegidoHumanoDTO> protegido = protegidoHumanoRepository.findByIdUsuario(id);
        if (protegido.isEmpty()){
            throw new BasicException("Usuario não encontrado na base de dados");
        }
        List<ProtegidoHumanoDTO> result = protegidoHumanoRepository.findByIdUsuario(id);
            return result;
    }
}
