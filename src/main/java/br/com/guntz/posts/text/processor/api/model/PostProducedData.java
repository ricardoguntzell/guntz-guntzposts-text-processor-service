package br.com.guntz.posts.text.processor.api.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class PostProducedData {

    private UUID postId;

    private Long wordCount;

    private BigDecimal calculatedValue;

}
