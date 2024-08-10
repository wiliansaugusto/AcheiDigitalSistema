package digital.achei.api.service;

import digital.achei.api.DTO.ContatoDTO;
import digital.achei.api.DTO.EnderecoDTO;
import digital.achei.api.DTO.ProtegidoHumanoDTO;
import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.ContatoRepository;
import digital.achei.api.repository.EnderecoRepository;
import digital.achei.api.repository.ProtegidoHumanoRepository;
import digital.achei.api.repository.UsuarioRepository;
import digital.achei.api.responses.UsarioProtegidos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ProtegidoHumanoRepository protegidoHumanoRepository;
    private static final Logger log = LogManager.getLogger(UsuarioService.class);

    public Optional<UsuarioDTO> salvarUsuario(UsuarioDTO usuarioDTO) {
        Optional<UsuarioDTO> resposta;
        verficarEmailECpf(usuarioDTO, "salvar");

        if (!isNull(usuarioDTO.getContato())) {
            for (ContatoDTO contato : usuarioDTO.getContato()) {
                contato.setUsuario(usuarioDTO);
            }
        }
        if (!isNull(usuarioDTO.getEndereco())) {
            for (EnderecoDTO enderecoDTO : usuarioDTO.getEndereco()) {
                enderecoDTO.setUsuario(usuarioDTO);
            }
        }
        try {
            log.info("Salvando Usuario: {}", usuarioDTO);
            resposta = Optional.of(usuarioRepository.save(usuarioDTO));

        } catch (BasicException e) {
            log.error(e);
            throw new BasicException("Probleamas com o banco de dados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resposta;
    }

    public Optional<UsuarioDTO> editarUsuario(UsuarioDTO usuarioDTO) {
        Optional<UsuarioDTO> usuarioEditar = usuarioRepository.findById(usuarioDTO.getIdUsuario());

        if (usuarioEditar.isEmpty()) {
            throw new BasicException("Usuario não encontrado na base de dados", HttpStatus.BAD_REQUEST);
        }

        verficarEmailECpf(usuarioDTO, "editar");

        if (usuarioDTO.getContato().isEmpty()) {
            List<ContatoDTO> contatos = new ArrayList<>();
            for (ContatoDTO contato : contatoRepository.findByIdUsuario(usuarioDTO.getIdUsuario())) {
                contatos.add(contato);
            }
            usuarioDTO.setContato(contatos);
        } else {
            List<ContatoDTO> contatosBanco = contatoRepository.findByIdUsuario(usuarioDTO.getIdUsuario());
            List<ContatoDTO> contatoComID = usuarioDTO.getContato().stream().filter(contatoDTO -> !isNull(contatoDTO.getIdContato())).collect(Collectors.toList());
            List<ContatoDTO> contatoSemID = usuarioDTO.getContato().stream().filter(contatoDTO -> isNull(contatoDTO.getIdContato())).collect(Collectors.toList());

            boolean todosPresentes = contatoComID.stream().allMatch(contatoComId -> contatosBanco.stream().anyMatch(contatoBanco -> contatoBanco.getIdContato().equals(contatoComId.getIdContato())));
            if (!todosPresentes) {
                throw new BasicException("Um ou mais contatos não estão presentes no banco", HttpStatus.CONFLICT);
            }

            List<ContatoDTO> listaDefinitiva = contatosBanco.stream().filter(contato -> contatoComID.stream().noneMatch(contatoDTO -> contatoDTO.getIdContato().equals(contato.getIdContato()))).collect(Collectors.toList());
            listaDefinitiva.addAll(contatoSemID);
            listaDefinitiva.addAll(contatoComID);
            usuarioDTO.setContato(listaDefinitiva);
        }
        if (usuarioDTO.getEndereco().isEmpty()) {
            List<EnderecoDTO> enderecos = new ArrayList<>();
            for (EnderecoDTO enderecoDTO : enderecoRepository.findByIdUsuarioEndereco(usuarioDTO.getIdUsuario())) {
                enderecos.add(enderecoDTO);

            }
            usuarioDTO.setEndereco(enderecos);
        } else {
            List<EnderecoDTO> enderecosBanco = enderecoRepository.findByIdUsuarioEndereco(usuarioDTO.getIdUsuario());
            List<EnderecoDTO> enderecosComID = usuarioDTO.getEndereco().stream().filter(enderecoDTO -> !isNull(enderecoDTO.getIdEndereco())).collect(Collectors.toList());
            List<EnderecoDTO> enderecosSemID = usuarioDTO.getEndereco().stream().filter(enderecoDTO -> isNull(enderecoDTO.getIdEndereco())).collect(Collectors.toList());

            boolean todosPresentes = enderecosComID.stream().allMatch(enderecoComId -> enderecosBanco.stream().anyMatch(enderecoBanco -> enderecoBanco.getIdEndereco().equals(enderecoComId.getIdEndereco())));
            if (!todosPresentes) {
                throw new BasicException("Um ou mais contatos não estão presentes no banco", HttpStatus.CONFLICT);
            }

            List<EnderecoDTO> listaDefinitiva = enderecosBanco.stream().
                    filter(endereco -> enderecosComID.stream()
                            .noneMatch(enderecoDTO -> enderecoDTO.getIdEndereco()
                                    .equals(endereco.getIdEndereco()))).collect(Collectors.toList());
            listaDefinitiva.addAll(enderecosSemID);
            listaDefinitiva.addAll(enderecosComID);
            usuarioDTO.setEndereco(listaDefinitiva);
        }


        try {

            log.info("editando o usuario: {}", usuarioDTO.getNomeCompleto());
            contatoRepository.saveAll(usuarioDTO.getContato());
            enderecoRepository.saveAll(usuarioDTO.getEndereco());
            usuarioEditar = Optional.of(usuarioRepository.save(usuarioDTO));
        } catch (BasicException e) {
            throw new BasicException("Probleamas com o banco de dados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return usuarioEditar;
    }

    public Optional<UsuarioDTO> alterarStatusUsuario(Long id) {
        Optional<UsuarioDTO> usuarioEditar = usuarioRepository.findById(id);
        if (usuarioEditar.isEmpty()) {
            throw new BasicException("Usuario não encontrado na base de dados", HttpStatus.BAD_REQUEST);
        }
        if (usuarioEditar.get().isAtivo() == Boolean.TRUE) {
            usuarioEditar.get().setAtivo(false);
        } else {
            usuarioEditar.get().setAtivo(true);
        }
        try {

            log.info("desativando o usuario: {}", usuarioEditar.get().getNomeCompleto());
            usuarioEditar = Optional.of(usuarioRepository.save(usuarioEditar.get()));
        } catch (BasicException e) {
            throw new BasicException("Probleamas com o banco de dados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return usuarioEditar;
    }

    public List<UsuarioDTO> listarTodosUsuarios() {
        List<UsuarioDTO> resposta;
        try {
            resposta = usuarioRepository.findAll();
        } catch (BasicException e) {
            throw new BasicException("Probleamas com o banco de dados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resposta;
    }

    public Optional<UsuarioDTO> listarUsuarioCpf(String cpf) {
        cpfIsValid(cpf);
        Optional<UsuarioDTO> resposta = Optional.of(usuarioRepository.findByCpf(cpf).orElseThrow(() -> {
            throw new BasicException("Usuario não encontrado");
        }));
        return resposta;
    }

    public Optional<UsuarioDTO> listarUsuarioId(Long id) {
        return Optional.of(usuarioRepository.findById(id).orElseThrow(() -> new BasicException("Usuario não encontrado", HttpStatus.BAD_REQUEST)));
    }

    public UsarioProtegidos listarUsuarioProtegido(Long id) {
        Optional<UsuarioDTO> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new BasicException("Usuario não encontrado");
        }

        List<ProtegidoHumanoDTO> protegidos = protegidoHumanoRepository.findByIdUsuario(id);

        if (protegidos.isEmpty()) {
            throw new BasicException("Protegidos não Cadastrados");
        }
        UsarioProtegidos result = new UsarioProtegidos();
        result.setUsuario(usuario.get());
        result.setProtegidos(protegidos);


        return result;

    }

    void verficarEmailECpf(UsuarioDTO usuarioDTO, String tipo) {
        Optional<UsuarioDTO> resposta = usuarioRepository.findByCpf(usuarioDTO.getCpf());
        Optional<UsuarioDTO> usuarioPorEmail = usuarioRepository.findByEmail(usuarioDTO.getEmail());

        if (tipo.equals("salvar")) {
            if (resposta.isPresent()) {
                log.info("CPF já cadastrado", usuarioDTO.getCpf());
                throw new BasicException("CPF ja cadastrado", HttpStatus.CONFLICT);
            }
            if (usuarioPorEmail.isPresent()) {
                log.info("Email já cadastrado", usuarioDTO.getCpf());
                throw new BasicException("Email ja cadastrado", HttpStatus.CONFLICT);
            }
        } else if (tipo.equals("editar")) {
            if ((resposta.isPresent() && !resposta.get().getIdUsuario().equals(usuarioDTO.getIdUsuario()))) {
                log.info("CPF já cadastrado", usuarioDTO.getCpf());
                throw new BasicException("CPF ja cadastrado", HttpStatus.CONFLICT);
            }
            if ((usuarioPorEmail.isPresent() && !usuarioPorEmail.get().getIdUsuario().equals(usuarioDTO.getIdUsuario()))) {
                log.info("Email já cadastrado", usuarioDTO.getEmail());
                throw new BasicException("Email ja cadastrado", HttpStatus.CONFLICT);
            }
        }


    }

    public void cpfIsValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            throw new BasicException("CPF invalido", HttpStatus.BAD_REQUEST);
        }

        try {
            char dig10, dig11;
            int sm, i, r, num, peso;

            // Calcula o 1º dígito verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);  // converte o char para int
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);  // converte int para char
            }

            // Calcula o 2º dígito verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os dígitos calculados conferem com os dígitos informados
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                log.info("CPF: {} valido ", cpf);
            } else {
                throw new BasicException("CPF invalido", HttpStatus.BAD_REQUEST);
            }
        } catch (InputMismatchException e) {
            throw new BasicException("CPF invalido", HttpStatus.BAD_REQUEST);
        }
    }
}
