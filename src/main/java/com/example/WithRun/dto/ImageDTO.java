package com.example.WithRun.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageDTO {
    private String url;
    private String filename;
}
