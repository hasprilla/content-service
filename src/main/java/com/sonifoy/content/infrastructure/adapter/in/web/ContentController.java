package com.sonifoy.content.infrastructure.adapter.in.web;

import com.sonifoy.content.application.service.ContentService;
import com.sonifoy.content.domain.model.Genre;
import com.sonifoy.content.domain.model.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/public/genres")
    public Flux<Genre> getAllGenres() {
        return contentService.getAllGenres();
    }

    @PostMapping("/genres")
    public Mono<Genre> createGenre(@RequestBody Genre genre) {
        return contentService.createGenre(genre);
    }

    @GetMapping("/public/tracks/{id}")
    public Mono<Track> getTrackById(@PathVariable Long id) {
        return contentService.getTrackById(id);
    }

    @GetMapping("/public/tracks/artist/{artistId}")
    public Flux<Track> getTracksByArtist(@PathVariable Long artistId) {
        return contentService.getTracksByArtist(artistId);
    }

    @PostMapping("/tracks")
    public Mono<Track> createTrack(@RequestBody Track track) {
        return contentService.createTrack(track);
    }

    @PostMapping(value = "/upload", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadFile(@RequestPart("file") org.springframework.http.codec.multipart.FilePart filePart) {
        return contentService.uploadFile(filePart);
    }
}
