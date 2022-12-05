package com.example.WithRun.domain;

import com.example.WithRun.dto.FreePostImageDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreePostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freepost_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freepost_id")
    private FreePost freePostImageFreePost;

    private String name;
    private String url;
    private LocalDateTime created_at;


    public FreePostImageDTO toDto(){
        return FreePostImageDTO.builder().id(id).name(name)
                .url(url).created_at(created_at).build();
    }

}
