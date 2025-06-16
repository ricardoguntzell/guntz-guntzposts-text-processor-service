package br.com.guntz.posts.text.processor.infrastructure.rabbitmq;

import br.com.guntz.posts.text.processor.api.model.PostData;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static br.com.guntz.posts.text.processor.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEXT_PROCESSOR_POST;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQListener {

    @SneakyThrows
    @RabbitListener(queues = QUEUE_PROCESS_TEXT_PROCESSOR_POST)
    public void handle(@Payload PostData postData) {
        Thread.sleep(Duration.ofSeconds(5000).toSeconds());

        log.info("Read message... postId: {} --- postBody: {}", postData.getPostId(), postData.getPostBody());
    }

}
