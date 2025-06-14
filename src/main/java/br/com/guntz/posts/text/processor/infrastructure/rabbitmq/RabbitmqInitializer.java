package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class RabbitmqInitializer {

    private final RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void init() {
        rabbitAdmin.initialize();
    }

}
