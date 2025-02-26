package digital.achei.api.service;

import digital.achei.api.DTO.UsuarioDTO;
import digital.achei.api.exception.BasicException;
import digital.achei.api.request.EnviaEmailRequest;
import digital.achei.api.request.EnvioEmailGenericoRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EnvioEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final Logger log = LogManager.getLogger(EnvioEmailService.class);

    public void enviaEmailContato(EnviaEmailRequest enviaEmailRequest) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nome", enviaEmailRequest.getNome());
        templateModel.put("menssage", enviaEmailRequest.getMensagem());
        templateModel.put("email", enviaEmailRequest.getEmail());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(templateModel);
        String html = templateEngine.process("email-template", context);

        helper.setTo(new String[]{"atendimentoacheidigital@gmail.com", "wiliansaugusto@gmail.com"});
        helper.setText(html, true);
        helper.setSubject("mensagem da Achei Digital");
        helper.setFrom("atendimentoacheidigital@gmail.com");

        try{
            mailSender.send(message);
            log.info("Enviado o email de {}",enviaEmailRequest.getEmail());

        }catch (BasicException e){
            throw new BasicException("Não foi possivel enviar o email", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public void enviaSenha(Optional<UsuarioDTO> usuarioDTO) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("nome", usuarioDTO.get().getNomeCompleto());
        templateModel.put("senha", usuarioDTO.get().getSenha());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(templateModel);
        String html = templateEngine.process("senha-template", context);

        helper.setTo(usuarioDTO.get().getEmail());
        helper.setText(html, true);
        helper.setSubject("mensagem da Achei Digital");
        helper.setFrom("atendimentoacheidigital@gmail.com");

        try{
            mailSender.send(message);
            log.info("Enviado o email de {}",usuarioDTO.get().getEmail());

        }catch (BasicException e){
            throw new BasicException("Não foi possivel enviar o email", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public void enviaEmailGenerico(String menssagem, ArrayList emails, MultipartFile pdfFile) throws MessagingException, IOException {

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("message", menssagem);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(templateModel);
        String html = templateEngine.process("generic-template", context);


        // Processa a lista de e-mails

        System.out.println("e\n\n"+emails);
        List<InternetAddress> addressList = new ArrayList<>();
        for (Object email : emails) {
            try {
                // Converte o e-mail para InternetAddress
                addressList.add(new InternetAddress((String) email));
            } catch (AddressException e) {
                throw new RuntimeException("Erro ao criar endereço de e-mail: " + email, e);
            }
        }

        // Define os destinatários
        helper.setTo(addressList.toArray(new InternetAddress[0]));

        helper.setText(html, true);
        helper.setSubject("mensagem da Achei Digital");
        helper.setFrom("atendimentoacheidigital@gmail.com");
        // Adicione o anexo PDF
        helper.addAttachment("documento.pdf", pdfFile);

        try{
            mailSender.send(message);
            log.info("Email Enviado");

        }catch (BasicException e){
            throw new BasicException("Não foi possivel enviar o email", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
