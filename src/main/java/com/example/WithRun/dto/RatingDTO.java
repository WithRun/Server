package com.example.WithRun.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private String targetId;
    private String comment;
    private String rate;
}
