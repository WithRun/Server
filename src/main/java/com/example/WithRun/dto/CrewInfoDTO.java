package com.example.WithRun.dto;

import com.example.WithRun.domain.CrewInfo;
import com.example.WithRun.domain.CrewInfoComment;
import com.example.WithRun.domain.CrewInfoImage;
import com.example.WithRun.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewInfoDTO {

    private Long id;
    private String title;
    private String content;


    public static CrewInfo toEntity(CrewInfoDTO dto){
        return CrewInfo.builder()
                .title(dto.title).content(dto.content)
                .build();
    }

}
