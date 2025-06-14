package br.com.guntz.posts.text.processor.infrastructure.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Guntz Text Processor Service - Microsserviço")
                        .description("""
                                Guntz Text Processor é um microsserviço de processamento de posts via Rabbitmq construído com Spring Boot.\n
                                Esse microsserviço vai consumir a fila <b>(text-processor-service.post-processing.v1.q)</b>.\n
                                Fica de sua responsabilidade fazer a contagem de palavras no texto e o cálculo de um valor estimado com base na quantidade de palavras.\n
                                Uma vez processado, o resultado será enviado para a fila <b>(post-service.post-processing-result.v1.q)</b> que por sua vez será consumido pelo microsserviço <b>Guntz Post Service</b>.
                                """
                        )
                        .contact(new Contact()
                                .name("Guntz")
                                .email("rricardoguntzell@gmail.com"))
                        .license(new License()
                                .name("Guntz - Github")
                                .url("https://github.com/ricardoguntzell/guntz-guntzposts-text-processor-service.git")));
    }
}
