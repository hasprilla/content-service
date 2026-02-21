package com.sonifoy.content.application.service;

import com.sonifoy.content.domain.model.Genre;
import com.sonifoy.content.domain.model.Track;
import com.sonifoy.content.infrastructure.adapter.out.persistence.GenreEntity;
import com.sonifoy.content.infrastructure.adapter.out.persistence.GenreRepository;
import com.sonifoy.content.infrastructure.adapter.out.persistence.TrackEntity;
import com.sonifoy.content.infrastructure.adapter.out.persistence.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final TrackRepository trackRepository;
    private final GenreRepository genreRepository;
    private final StorageService storageService; // Injected

    // Genres
    public Flux<Genre> getAllGenres() {
        return genreRepository.findAll().map(this::mapGenreToDomain);
    }

    public Mono<Genre> createGenre(Genre genre) {
        return genreRepository.save(GenreEntity.builder()
                .name(genre.getName())
                .build())
                .map(this::mapGenreToDomain);
    }

    // Tracks
    public Flux<Track> getTracksByArtist(Long artistId) {
        return trackRepository.findByArtistId(artistId).map(this::mapTrackToDomain);
    }

    public Mono<Track> createTrack(Track track) {
        TrackEntity entity = mapTrackToEntity(track);
        entity.setCreatedAt(Instant.now());
        // default funding stuff if null
        return trackRepository.save(entity).map(this::mapTrackToDomain);
    }

    public Mono<Track> getTrackById(Long id) {
        return trackRepository.findById(id).map(this::mapTrackToDomain);
    }

    public Mono<String> uploadFile(org.springframework.http.codec.multipart.FilePart filePart) {
        return storageService.uploadFile(filePart);
    }

    // Mappers
    private Genre mapGenreToDomain(GenreEntity entity) {
        return Genre.builder().id(entity.getId()).name(entity.getName()).build();
    }

    private Track mapTrackToDomain(TrackEntity entity) {
        return Track.builder()
                .id(entity.getId())
                .artistId(entity.getArtistId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .audioUrl(entity.getAudioUrl())
                .imageUrl(entity.getImageUrl())
                .fundingGoal(entity.getFundingGoal())
                .currentFunding(entity.getCurrentFunding())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private TrackEntity mapTrackToEntity(Track domain) {
        return TrackEntity.builder()
                .id(domain.getId())
                .artistId(domain.getArtistId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .audioUrl(domain.getAudioUrl())
                .imageUrl(domain.getImageUrl())
                .fundingGoal(domain.getFundingGoal())
                .currentFunding(domain.getCurrentFunding())
                .build();
    }
}
