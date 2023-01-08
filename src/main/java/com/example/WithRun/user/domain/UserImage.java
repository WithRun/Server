package com.example.WithRun.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "myImage")
    private User user;

    private String name;
    private String url;
}
