package com.sonifoy.content.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {
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
