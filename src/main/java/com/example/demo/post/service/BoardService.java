package com.example.demo.post.service;


import com.example.demo.post.domain.Board;
import com.example.demo.post.domain.Comment;
import com.example.demo.post.dto.BoardRequestDto;
import com.example.demo.post.dto.BoardResponseDto;
import com.example.demo.post.dto.CommentDto;
import com.example.demo.post.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardRequestDto boardRequestDto) {
        return boardRepository.save(boardRequestDto.toEntity()).getId();
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : board.getComments()) {
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .writer(comment.getWriter())
                    .createdTime(comment.getCreatedTime())
                    .modifiedTime(comment.getModifiedTime())
                    .build();
            commentDtoList.add(commentDto);
        }

        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(board.getWriter())
                .comments(commentDtoList)
                .createdTime(board.getCreatedTime())
                .modifiedTime(board.getModifiedTime())
                .build();

        return boardResponseDto;
    }

    @Transactional
    public List<BoardResponseDto> getboardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : boards) {
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .contents(board.getContents())
                    .writer(board.getWriter())
                    .createdTime(board.getCreatedTime())
                    .build();
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    @Transactional
    public Long updatePost(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. " + id));
        board.update(boardRequestDto);

        return id;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardResponseDto> searchPosts(String type, String keyword) {
        List<Board> boards;
        if (type.equals("title")) {
            boards = boardRepository.findByTitleContaining(keyword);
        } else {
            boards = boardRepository.findByWriterContaining(keyword);
        }

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        if (boards.isEmpty()) return boardResponseDtoList;
        for (Board board : boards) {
            boardResponseDtoList.add(this.convertEntityToDto(board));
        }

        return boardResponseDtoList;
    }

    private BoardResponseDto convertEntityToDto(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(board.getWriter())
                .createdTime(board.getCreatedTime())
                .modifiedTime(board.getModifiedTime())
                .build();
    }
}
