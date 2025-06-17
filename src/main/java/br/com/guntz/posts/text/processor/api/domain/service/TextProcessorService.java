package br.com.guntz.posts.text.processor.api.domain.service;

import br.com.guntz.posts.text.processor.api.model.PostProducedData;
import br.com.guntz.posts.text.processor.api.model.PostReceivedData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.guntz.posts.text.processor.infrastructure.rabbitmq.RabbitMQConfig.FONOUT_EXCHANGE_TEXT_PROCESSOR_RECEIVED;

@Slf4j
@AllArgsConstructor
@Service
public class TextProcessorService {

    private final RabbitTemplate rabbitTemplate;

    public void processPostReading(PostReceivedData postReceivedData) {
        log.info("Post Processed: {}", postReceivedData.getPostId());
        calculatePost(postReceivedData);
    }

    public void calculatePost(PostReceivedData postReceivedData) {
        log.info("Post Calculated: {}", postReceivedData.getPostId());

        var sizeWords = countWords(postReceivedData.getPostBody());
        var estimatedPrice = calculateEstimatedPrice(sizeWords);

        PostProducedData postProducedData = PostProducedData.builder()
                .postId(postReceivedData.getPostId())
                .wordCount(sizeWords)
                .calculatedValue(estimatedPrice)
                .build();

        sendPostProcessed(postProducedData);
    }

    public void sendPostProcessed(PostProducedData postProducedData) {
        rabbitTemplate.convertAndSend(FONOUT_EXCHANGE_TEXT_PROCESSOR_RECEIVED, "", postProducedData);
        log.info("Post {} inserted in exchange: {}", postProducedData.getPostId(), FONOUT_EXCHANGE_TEXT_PROCESSOR_RECEIVED);
    }

    private Long countWords(String word) {
        if (!word.isBlank()) {
            return Long.valueOf(word.length());
        }

        return 0L;
    }

    private BigDecimal calculateEstimatedPrice(Long sizeWords) {
        if (sizeWords > 0) {
            BigDecimal estimatedValueToMultiply = new BigDecimal("0.10");
            return estimatedValueToMultiply.multiply(BigDecimal.valueOf(sizeWords));
        }

        return BigDecimal.ZERO;
    }


}
