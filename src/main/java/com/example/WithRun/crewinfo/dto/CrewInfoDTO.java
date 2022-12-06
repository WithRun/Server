package com.example.WithRun.crewinfo.dto;

import com.example.WithRun.crewinfo.domain.CrewInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewInfoDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String latitude;
    private String longitude;

    public static CrewInfo toEntity(CrewInfoDTO dto){
        return CrewInfo.builder()
                .title(dto.title).content(dto.content).author(dto.author)
                .latitude(dto.latitude).longitude(dto.longitude)
                .build();
    }

}
