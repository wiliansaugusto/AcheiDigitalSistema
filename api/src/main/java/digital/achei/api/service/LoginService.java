package digital.achei.api.service;

import digital.achei.api.DTO.LoginDTO;
import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class LoginService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EnvioEmailService envioEmailService;

    private static final Logger log = LogManager.getLogger(LoginService.class);

    public UsuarioDTO login(LoginDTO loginDTO) {
        System.out.println(loginDTO);
        if (isNull(loginDTO.getCpf()) & isNull(loginDTO.getEmail()))
            throw new BasicException("CPF ou Email não informados", HttpStatus.BAD_REQUEST);
        UsuarioDTO respostaLogin;
        if (!isNull(loginDTO.getCpf())) {
            log.info("Campos Validados com sucesso cpf: {} e senha {}", loginDTO.getCpf(), loginDTO.getSenha());
            try {
                respostaLogin = usuarioRepository.loginCPF(loginDTO.getCpf(), loginDTO.getSenha());

            } catch (BasicException e) {
                throw new BasicException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.info("Campos Validados com sucesso email: {} e senha {}", loginDTO.getEmail(), loginDTO.getSenha());
            try {
                respostaLogin = usuarioRepository.loginEmail(loginDTO.getEmail(), loginDTO.getSenha());

            } catch (BasicException e) {
                throw new BasicException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        if (isNull(respostaLogin)){
            throw new BasicException("Usuário não encontrado na base de dados", HttpStatus.NOT_ACCEPTABLE);

        }
        return respostaLogin;
    }

    public void esqueciSenha(String email){
        Optional<UsuarioDTO> resp = usuarioRepository.findByEmail(email);
        if (resp.isEmpty()){
            throw new BasicException("Usuário não encontrado",HttpStatus.BAD_REQUEST);
        }
        SecureRandom random = new SecureRandom();
        int number = 10000 + random.nextInt(90000);
        String senhaNova = "@Chei".concat(String.valueOf(number));
        resp.get().setSenha(senhaNova);
        usuarioRepository.save(resp.get());

        try{
            Object teste = "Email Enviado com sucesso";
            envioEmailService.enviaSenha(resp);
        }catch (MessagingException e){
            throw new BasicException("Não foi possivel enviar o email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
