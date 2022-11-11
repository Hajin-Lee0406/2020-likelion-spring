package com.example.demo.post.dto;

import com.example.demo.post.domain.Board;
import com.example.demo.post.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;

    private String title;

    private String contents;

    private String writer;

    private List<CommentDto> comments;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    @Builder
    public BoardResponseDto(Long id, String title, String contents, String writer, List<CommentDto> comments, LocalDateTime createdTime, LocalDateTime modifiedTime){
        this.id=id;
        this.title=title;
        this.contents=contents;
        this.writer = writer;
        this.comments = comments;
        this.createdTime=createdTime;
        this.modifiedTime=modifiedTime;
    }
}
