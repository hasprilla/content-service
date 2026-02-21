package com.sonifoy.content.infrastructure.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;

@Table("tracks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackEntity {
    @Id
    private Long id;
    private Long artistId;
    private String title;
    private String description;
    private String audioUrl;
    private String imageUrl;
    private BigDecimal fundingGoal;
    private BigDecimal currentFunding;
    private Instant createdAt;
}
