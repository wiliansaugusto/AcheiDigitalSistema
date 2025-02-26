package digital.achei.api.service;

import digital.achei.api.DTO.*;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.ContatoRepository;
import digital.achei.api.repository.EnderecoRepository;
import digital.achei.api.repository.ProtegidoPetRepository;
import digital.achei.api.repository.TagPetRepository;
import digital.achei.api.responses.ProtegidoPetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ProtegidoPetService {
    @Autowired
    ProtegidoPetRepository protegidoPetRepository;
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    TagPetRepository tagPetRepository;
    private static final Logger log = LogManager.getLogger(ProtegidoPetService.class);

    public ProtegidoPetDTO salvarProtegidoPet(ProtegidoPetDTO protegidoPetDTO){
        if (protegidoPetDTO.getUsuario().getIdUsuario().equals(null)){
            throw new BasicException("Id do Usuario nao foi informado");
        }
        ProtegidoPetDTO result = protegidoPetRepository.save(protegidoPetDTO);
        return result;
    }
    public ProtegidoPetDTO editarProtegidoPet(ProtegidoPetDTO protegidoPetDTO) {

        if (isNull(protegidoPetDTO.getNomeProtegidoPet())) {
            throw new BasicException("Id do protegido nao foi informado");
        }
        Optional<ProtegidoPetDTO> protegidoBusca = protegidoPetRepository.findById(protegidoPetDTO.getIdProtegidoPet());
        if (protegidoBusca.isEmpty()) {
            throw new BasicException("Id do protegido nao foi encontrado na base de dados");

        }
        if (!protegidoBusca.get().getUsuario().getIdUsuario().equals(protegidoPetDTO.getUsuario().getIdUsuario())){
            throw new BasicException("Usuário diferente do cadastrado no banco");
        }

        return protegidoPetRepository.save(protegidoPetDTO);
    }

    public ProtegidoPetResponse pesquisarProtegidoPet(Long id){
        Optional<ProtegidoPetDTO> protegidoPetDto = protegidoPetRepository.findById(id);
        if (protegidoPetDto.isEmpty()){
            throw new BasicException("Protegido com o id não encontrado", HttpStatus.BAD_REQUEST);
        }
        List<ContatoDTO> contatos = contatoRepository.findByIdUsuario(protegidoPetDto.get().getUsuario().getIdUsuario());
        List<EnderecoDTO> enderecos = enderecoRepository.findByIdUsuarioEndereco(protegidoPetDto.get().getUsuario().getIdUsuario());
        ProtegidoPetResponse response = new ProtegidoPetResponse();
        response.setProtegido(protegidoPetDto.get());
        response.setContato(contatos);
        response.setEndereco(enderecos);
        response.setEmail(protegidoPetDto.get().getUsuario().getEmail());
        response.setNomeUsuario(protegidoPetDto.get().getUsuario().getNomeCompleto());
        return response;
    }
    public List<ProtegidoPetDTO> pesquisarProtegidoPetPorUsuario(Long id){
        log.info("Procurando o usuario com ID: {}", id);
        List<ProtegidoPetDTO> protegido = protegidoPetRepository.findByIdUsuario(id);
        if (protegido.isEmpty()){
            throw new BasicException("Usuario não encontrado na base de dados");
        }
        return protegido;
    }
    public Optional<ProtegidoPetDTO> deletarProtegidoPet(Long id){
        log.info("Procurando o usuario com ID: {}", id);
        Optional<ProtegidoPetDTO> protegido = protegidoPetRepository.findById(id);
        if (protegido.isEmpty()){
            throw new BasicException("Usuario não encontrado na base de dados");
        }
        List<TagPetDTO> tagProtegidos = tagPetRepository.findByIdUsuario(protegido.get().getUsuario().getIdUsuario());
        List<TagPetDTO> tagProtegdosDEl = tagProtegidos.stream().filter(r -> r.getProtegidoPet().getIdProtegidoPet().equals(id)).toList();
        log.info("O usuario encontrado: {} com id {} será deletado", protegido.get().getNomeProtegidoPet(), protegido.get().getIdProtegidoPet());
        for (TagPetDTO tag : tagProtegdosDEl){
            log.info(tag.getIdTagPet());
            tagPetRepository.delete(tag);
        }
        protegidoPetRepository.delete(protegido.get());
        log.info("Deletado com success");
        return protegidoPetRepository.findById(protegido.get().getIdProtegidoPet());
    }

}
