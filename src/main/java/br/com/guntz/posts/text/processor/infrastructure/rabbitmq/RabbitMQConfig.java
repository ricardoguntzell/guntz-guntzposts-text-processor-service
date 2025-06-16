package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String PROCESS_TEXT_PROCESSOR_POST = "text-processor-service.post-processing.v1";
    public static final String QUEUE_PROCESS_POST = PROCESS_TEXT_PROCESSOR_POST + ".q";
    public static final String DEAD_LETTER_QUEUE_PROCESS_POST = PROCESS_TEXT_PROCESSOR_POST + ".dlq";
//    public static final String FONOUT_EXCHANGE_POST = "post-service.post-received.v1.e";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueTextProcessorPost() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_PROCESS_POST);

        return QueueBuilder
                .durable(QUEUE_PROCESS_POST)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue deadLetterQueueProcessPost() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE_PROCESS_POST)
                .build();
    }

//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queueTextProcessorPost()).to(exchange());
//    }
//

//    public FanoutExchange exchange() {
//        return ExchangeBuilder.
//                fanoutExchange(FONOUT_EXCHANGE_POST)
//                .build();
//    }

}
