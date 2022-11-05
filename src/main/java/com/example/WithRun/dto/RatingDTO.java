package com.example.WithRun.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private String targetUserId;
    private String comment;
    private String rate;
}
