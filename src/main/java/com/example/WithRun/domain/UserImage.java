package com.example.WithRun.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImage {

    @Id
    @GeneratedValue
    @Column(name = "user_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "myImage")
    private User user;

    private String name;
    private String url;
}
