package com.bellisaidev.platform.document_api.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class S3PresignService {

    private final S3Presigner presigner;

    public S3PresignService(S3Presigner presigner) {
        this.presigner = presigner;
    }

    public PresignedUpload presignPut(String bucket, String key, String contentType, long expirySeconds) {
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(expirySeconds))
                .putObjectRequest(putReq)
                .build();

        String url = presigner.presignPutObject(presignReq).url().toString();

        // Client must send Content-Type, otherwise S3 may reject or store incorrect metadata.
        return new PresignedUpload(url, contentType, expirySeconds);
    }

    public record PresignedUpload(String url, String requiredContentType, long expiresInSeconds) {}
}
