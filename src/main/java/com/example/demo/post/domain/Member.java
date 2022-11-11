package com.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

@NoArgsConstructor
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    @Column(length = 100)
    private String password;

    @Builder
    public Member(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
