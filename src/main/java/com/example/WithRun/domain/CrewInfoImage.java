package com.example.WithRun.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "json_id")
public class CrewInfoImage {

    @Id
    @GeneratedValue
    @Column(name = "crewinfo_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crewinfo_id")
    private CrewInfo crewInfoFromImage;

    private String name;
    private String url;
    private LocalDateTime created_at;
}
