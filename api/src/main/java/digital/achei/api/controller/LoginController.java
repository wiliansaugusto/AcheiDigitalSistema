package digital.achei.api.controller;

import digital.achei.api.DTO.LoginDTO;
import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.request.EnvioDeSenhaRequest;
import digital.achei.api.service.LoginService;
import digital.achei.api.utils.Utils;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.100:4200"})
public class LoginController {
    private static final Logger log = LogManager.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @Autowired
    Utils utils;

    @PostMapping("logar")
    public ResponseEntity getUsuario(@Valid @RequestBody LoginDTO loginDTO) {
        UsuarioDTO resposta = loginService.login(loginDTO);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping("esqueci-a-senha")
    public ResponseEntity esqueciSenha(@Valid @RequestBody EnvioDeSenhaRequest email) {
        loginService.esqueciSenha(email.getEmail());

        return new ResponseEntity<>(utils.createSimpleJson("Email enviado com sucesso"), HttpStatus.OK);
    }
}
