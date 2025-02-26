package digital.achei.api.service;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.controller.ContatoController;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.ContatoRepository;
import digital.achei.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ContatoService {
    private static final Logger log = LogManager.getLogger(ContatoController.class);

    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    public ContatoDTO deletarContato(Long idContato, Long idUsuario) {
        log.info("pesquisando contato");
        Optional<ContatoDTO> contato = contatoRepository.findById(idContato);
        if (contato.isPresent() && contato.get().getUsuario().getIdUsuario().equals(idUsuario)) {
            log.info("Contato com ID encontrado {}", contato.get().getIdContato());
        } else {
            throw new BasicException("Contato não encontrado", HttpStatus.BAD_REQUEST);
        }

        try {
            contatoRepository.deleteByIdContato(idContato);
            log.info("Contato com ID deletado {} com sucesso", contato.get().getIdContato());

        } catch (BasicException e) {
            throw new BasicException("Problemas com infraestrutura, contate o Administrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return contatoRepository.findById(idContato).get();

    }

    public ContatoDTO editarContato(ContatoDTO contatoDTO, Long idUsuario) {
        ContatoDTO resp = null;
        if (isNull(idUsuario)) {
            throw new BasicException("Id Usuario esta nullo");
        }
        if (isNull(contatoDTO.getIdContato())) {
            log.info("contato com id nullo");
            Optional<UsuarioDTO> usuario = Optional.of(usuarioRepository.findById(idUsuario).orElseThrow(() -> new BasicException("Usuário não encontrado", HttpStatus.BAD_REQUEST)));
            contatoDTO.setUsuario(usuario.get());
            resp = contatoRepository.save(contatoDTO);
            return resp;
        }
        log.info("pesquisando contato com id {}", contatoDTO.getIdContato());
        Optional<ContatoDTO> contatoEditado = contatoRepository.findById(contatoDTO.getIdContato());
        if (contatoEditado.isPresent() && contatoEditado.get().getUsuario().getIdUsuario().equals(idUsuario)) {
            log.info("Contato encontrado");
            UsuarioDTO usarioDTO = new UsuarioDTO();
            usarioDTO.setIdUsuario(idUsuario);
            contatoDTO.setUsuario(usarioDTO);
            contatoEditado = Optional.of(contatoRepository.save(contatoDTO));
            return contatoEditado.get();
        }

        return Optional.of(resp).orElseThrow(() -> new BasicException("Entre em contato com o Administrador", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
