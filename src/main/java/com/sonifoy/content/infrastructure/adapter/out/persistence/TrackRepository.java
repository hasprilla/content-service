package com.sonifoy.content.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface TrackRepository extends R2dbcRepository<TrackEntity, Long> {
    Flux<TrackEntity> findByArtistId(Long artistId);
}
