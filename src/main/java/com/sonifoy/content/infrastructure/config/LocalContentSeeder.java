package com.sonifoy.content.infrastructure.config;

import com.github.javafaker.Faker;
import com.sonifoy.content.infrastructure.adapter.out.persistence.TrackEntity;
import com.sonifoy.content.infrastructure.adapter.out.persistence.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;

@Component
@Profile("local")
@RequiredArgsConstructor
@Slf4j
public class LocalContentSeeder implements CommandLineRunner {

    private final TrackRepository trackRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private static final int ARTIST_COUNT = 50;
    private static final int MAX_TRACKS_PER_ARTIST = 5;

    @Override
    public void run(String... args) {
        log.info("LocalContentSeeder execution started.");
        trackRepository.count()
                .flatMap(count -> {
                    if (count > 0) {
                        log.info("Content database already seeded.");
                        return Mono.empty();
                    }
                    log.info("Seeding tracks...");
                    return seedTracks();
                })
                .subscribe();
    }

    private Mono<Void> seedTracks() {
        return Flux.range(1, ARTIST_COUNT)
                .flatMap(artistId -> {
                    int trackCount = random.nextInt(MAX_TRACKS_PER_ARTIST) + 1;
                    return Flux.range(1, trackCount)
                            .flatMap(k -> {
                                TrackEntity track = TrackEntity.builder()
                                        .artistId((long) artistId)
                                        .title(faker.music().genre() + " - " + faker.ancient().god())
                                        .description(faker.lorem().sentence())
                                        .audioUrl("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                                        .imageUrl("https://picsum.photos/seed/" + artistId + k + "/300/300")
                                        .fundingGoal(BigDecimal.valueOf(faker.number().numberBetween(1000, 50000)))
                                        .currentFunding(BigDecimal.valueOf(faker.number().numberBetween(0, 1000)))
                                        .build();
                                return trackRepository.save(track);
                            });
                })
                .then()
                .doOnSuccess(v -> log.info("Track seeding completed successfully."));
    }
}
