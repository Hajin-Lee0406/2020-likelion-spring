package com.example.demo.post.domain;

import com.example.demo.post.dto.BoardRequestDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String writer;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 정렬
    private List<Comment> comments;

    @Builder
    public Board(Long id, String title, String contents, String writer){
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public void update(BoardRequestDto boardDto){
        this.title = boardDto.getTitle();
        this.contents = boardDto.getContents();
    }
}
