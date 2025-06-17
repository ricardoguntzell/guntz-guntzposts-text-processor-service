package br.com.guntz.posts.text.processor.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PostReceivedData {

    private UUID postId;

    private String postBody;

}
