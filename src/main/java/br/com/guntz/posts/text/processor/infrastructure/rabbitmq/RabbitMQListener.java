package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import br.com.guntz.posts.text.processor.api.domain.service.TextProcessorService;
import br.com.guntz.posts.text.processor.api.model.PostReceivedData;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.guntz.posts.text.processor.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEXT_PROCESSOR_POST;

@AllArgsConstructor
@Component
public class RabbitMQListener {

    private final TextProcessorService textProcessorService;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_PROCESS_TEXT_PROCESSOR_POST, concurrency = "2-3")
    public void handleProcessPost(@Payload PostReceivedData postReceivedData) {
        textProcessorService.processPostReading(postReceivedData);
    }

}
