package com.example.WithRun.domain;

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
    @GeneratedValue
    @Column(name = "freepost_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freepost_id")
    private FreePost freePostFromImage;

    private String name;
    private String url;
    private LocalDateTime created_at;
}
