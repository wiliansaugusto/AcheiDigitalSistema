package digital.achei.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Achei Digital")
                        .description("Esse api foi desenvolvida para o produto de tags do achei digital")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Willians Augusto")
                                .url("https://williansaugusto.achei.digital")
                                .email("wiliansaugusto@gmail.com")))
                        .externalDocs(new ExternalDocumentation()
                        .description("Whatsapp")
                        .url("https://api.whatsapp.com/send/?phone=5513997161619"));
    }

}
