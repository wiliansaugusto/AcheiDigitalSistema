package digital.achei.api.controller;

import digital.achei.api.request.EnviaEmailRequest;
import digital.achei.api.service.EnvioEmailService;
import digital.achei.api.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.105:4200"})
public class EnvioEmailController {
    private static final Logger log = LogManager.getLogger(EnvioEmailController.class);

    @Autowired
    EnvioEmailService envioEmailService;


    @PostMapping("enviar-email")
    ResponseEntity enviarEmail(@Valid @RequestBody EnviaEmailRequest enviaEmailRequest)   {
        log.info("enviando email");
        try{
            envioEmailService.enviaEmailContato(enviaEmailRequest);
        }catch (MessagingException e){
            log.info("problemas para enviar o email");
        }
        return new ResponseEntity<>(new Utils().createSimpleJson("Email enviado com sucesso"), HttpStatus.OK);
    }

}
