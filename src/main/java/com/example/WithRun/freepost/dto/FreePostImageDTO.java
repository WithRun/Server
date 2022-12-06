package com.example.WithRun.freepost.dto;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreePostImageDTO {


    private long id;
    private String name;
    private String url;
    private LocalDateTime created_at;
}
