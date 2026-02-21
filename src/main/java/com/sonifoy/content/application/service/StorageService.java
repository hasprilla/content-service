package com.sonifoy.content.application.service;

import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.FilePart;

public interface StorageService {
    Mono<String> uploadFile(FilePart filePart);
}
