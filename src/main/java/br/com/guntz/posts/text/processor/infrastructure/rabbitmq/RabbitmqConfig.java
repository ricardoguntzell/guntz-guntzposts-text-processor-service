package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    private static final String PROCESS_POST = "text-processor-service.post-processing.v1";
    public static final String QUEUE_PROCESS_POST = PROCESS_POST + ".q";
    public static final String DEAD_LETTER_QUEUE_PROCESS_POST = PROCESS_POST + ".dlq";
    public static final String FONOUT_EXCHANGE = "text-processor-processing.text-processor-received.v1.e";

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

    @Bean
    public FanoutExchange exchange() {
        return ExchangeBuilder.
                fanoutExchange(FONOUT_EXCHANGE)
                .build();
    }

}
