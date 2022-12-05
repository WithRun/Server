package com.example.WithRun.dto;

import com.example.WithRun.domain.FreePostComment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreePostDTO {

    private long id;
    private String title;
    private String content;
    private String author;
    private List<FreePostCommentDTO> freePostCommentDTOList;
    private FreePostImageDTO freePostImageDTO;
    private LocalDateTime created_at;


    public FreePostDTO add(FreePostImageDTO dto, List<FreePostCommentDTO> dtoList){
        this.setFreePostImageDTO(dto);
        this.setFreePostCommentDTOList(dtoList);
        return this;
    }

}
