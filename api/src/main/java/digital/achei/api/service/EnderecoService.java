package digital.achei.api.service;

import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.EnderecoRepository;
import digital.achei.api.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class EnderecoService {
    private static final Logger log = LogManager.getLogger(EnderecoService.class);

    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    public EnderecoDTO deletarEndereco(Long idEndereco, Long idUsuario) {
        log.info("pesquisando endereco");
        Optional<EnderecoDTO> endereco = enderecoRepository.findById(idEndereco);
        if (endereco.isPresent() && endereco.get().getUsuario().getIdUsuario().equals(idUsuario)) {
            log.info("Endereco com ID encontrado {}", endereco.get().getIdEndereco());
        } else {
            throw new BasicException("Endereço não encontrado", HttpStatus.BAD_REQUEST);
        }

        try {
            enderecoRepository.deleteByIdEndereco(idEndereco);
            log.info("Enderecço com ID{} deletado com sucesso", endereco.get().getIdEndereco());

        } catch (BasicException e) {
            throw new BasicException("Problemas com infraestrutura, contate o Administrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return enderecoRepository.findById(idEndereco).get();
    }

    public EnderecoDTO editarEndereco(EnderecoDTO enderecoDTO, Long idUsuario) {
        EnderecoDTO resp = null;
        if (isNull(idUsuario)) {
            throw new BasicException("Id Usuario esta nullo");
        }
        if (isNull(enderecoDTO.getIdEndereco())) {
            log.info("Endereco com id nullo");
            Optional<UsuarioDTO> usuario = Optional.of(usuarioRepository.findById(idUsuario).orElseThrow(() -> new BasicException("Usuário não encontrado", HttpStatus.BAD_REQUEST)));
            enderecoDTO.setUsuario(usuario.get());
            resp = enderecoRepository.save(enderecoDTO);
            return resp;
        }
        log.info("pesquisando Endereço com id {}", enderecoDTO.getIdEndereco());
        Optional<EnderecoDTO> enderecoEditado = enderecoRepository.findById(enderecoDTO.getIdEndereco());
        if (enderecoEditado.isPresent() && enderecoEditado.get().getUsuario().getIdUsuario().equals(idUsuario)) {
            log.info("Contato encontrado");
            UsuarioDTO usarioDTO = new UsuarioDTO();
            usarioDTO.setIdUsuario(idUsuario);
            enderecoDTO.setUsuario(usarioDTO);
            enderecoEditado = Optional.of(enderecoRepository.save(enderecoDTO));
            return enderecoEditado.get();
        }

        return Optional.ofNullable(resp).orElseThrow(() -> new BasicException("Entre em contato com o Administrador", HttpStatus.INTERNAL_SERVER_ERROR));

    }
}
