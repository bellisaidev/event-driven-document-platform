package com.bellisaidev.platform.document_api.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DocumentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.documentUploaded}")
    private String documentUploadedTopic;

    public void publishDocumentUploaded(DocumentUploadedEvent event) {
        // key by document id => ordering per document in the topic partition
        kafkaTemplate.send(documentUploadedTopic, event.document().id().toString(), event);
    }
}
