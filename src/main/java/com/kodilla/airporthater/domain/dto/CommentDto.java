package com.kodilla.airporthater.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private String title;
    private String body;
    private int score;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private Long userId;
    private String airportId;
}
