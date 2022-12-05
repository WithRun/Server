package com.example.WithRun.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePostCommentDTO {

    private long id;
    private String author;
    private String content;
    private LocalDateTime created_at;
}
