package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String PROCESS_TEXT_PROCESSOR_RECEIVED = "text-processor.post-received.v1";
    private static final String PROCESS_TEXT_PROCESSOR_POST = "text-processor-service.post-processing.v1";
    public static final String QUEUE_PROCESS_TEXT_PROCESSOR_POST = PROCESS_TEXT_PROCESSOR_POST + ".q";
    public static final String DEAD_LETTER_QUEUE_PROCESS_POST = PROCESS_TEXT_PROCESSOR_POST + ".dlq";
    public static final String FONOUT_EXCHANGE_TEXT_PROCESSOR_RECEIVED = PROCESS_TEXT_PROCESSOR_RECEIVED + ".e";

    //post-service
    private static final String QUEUE_POST_PROCESSING_RESULT = "post-service.post-processing-result.v1.q";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue queueTextProcessorPost() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_PROCESS_POST);

        return QueueBuilder
                .durable(QUEUE_PROCESS_TEXT_PROCESSOR_POST)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue deadLetterQueueProcessPost() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE_PROCESS_POST)
                .build();
    }

    public Queue queuePostProcessingResult() {
        return QueueBuilder
                .durable(QUEUE_POST_PROCESSING_RESULT)
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queuePostProcessingResult()).to(exchange());
    }

    @Bean
    public FanoutExchange exchange() {
        return ExchangeBuilder
                .fanoutExchange(FONOUT_EXCHANGE_TEXT_PROCESSOR_RECEIVED)
                .build();
    }


}
