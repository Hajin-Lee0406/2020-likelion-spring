package com.example.demo.post.service;

import com.example.demo.post.dto.BoardDto;
import com.example.demo.post.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDto boardDto){
        return boardRepository.save(boardDto.toEntity()).getId();
    }
}
