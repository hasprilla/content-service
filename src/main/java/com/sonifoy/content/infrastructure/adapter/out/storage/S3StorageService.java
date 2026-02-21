package com.sonifoy.content.infrastructure.adapter.out.storage;

import com.sonifoy.content.application.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService implements StorageService {

    private final S3AsyncClient s3AsyncClient;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public Mono<String> uploadFile(FilePart filePart) {
        String filename = UUID.randomUUID() + "-" + filePart.filename();

        return Mono.fromFuture(() -> {
            return s3AsyncClient.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(filename)
                            .contentType(filePart.headers().getContentType().toString())
                            .build(),
                    AsyncRequestBody.fromPublisher(
                            filePart.content().map(data -> {
                                ByteBuffer buffer = data.asByteBuffer();
                                return buffer;
                            })));
        })
                .doOnSuccess(resp -> log.info("Uploaded file {} to S3", filename))
                .doOnError(err -> log.error("Failed to upload file to S3", err))
                .map(resp -> filename); // Return the filename/key so we can store it or generate a URL
    }
}
