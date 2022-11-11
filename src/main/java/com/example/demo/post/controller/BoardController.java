package com.example.demo.post.controller;


import com.example.demo.post.domain.Comment;
import com.example.demo.post.dto.BoardRequestDto;
import com.example.demo.post.dto.BoardResponseDto;
import com.example.demo.post.dto.CommentDto;
import com.example.demo.post.service.BoardService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService=boardService;
    }

    @GetMapping("/")
    public String list(Model model){
        List<BoardResponseDto> boardResponseDtoList = boardService.getboardList();
        model.addAttribute("boardResponseDtoList", boardResponseDtoList);

        return "board/list.html";
    }

    @GetMapping("/post")
    public String write(){
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardRequestDto boardRequestDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardRequestDto.setWriter(username);
        boardService.savePost(boardRequestDto);
        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.getPost(id);
        List<CommentDto> comments = boardResponseDto.getComments();
        if(comments != null && !comments.isEmpty()){
            model.addAttribute("comments", comments);
        }
        model.addAttribute("boardResponseDto", boardResponseDto);
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long id, Model model){
        BoardResponseDto boardResponseDto = boardService.getPost(id);
        model.addAttribute("boardResponseDto", boardResponseDto);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(@PathVariable("no") Long id, BoardRequestDto boardRequestDto){
        boardService.updatePost(id, boardRequestDto);
        return "redirect:/post/{no}";
    }

    @DeleteMapping("post/delete/{no}")
    public String delete(@PathVariable("no") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("post/search")
    public String search(@RequestParam(value = "type") String type, @RequestParam(value = "keyword") String keyword, Model model){
        List<BoardResponseDto> boardResponseDtoList = boardService.searchPosts(type, keyword);
        model.addAttribute("boardResponseDtoList", boardResponseDtoList);

        return "board/list.html";
    }

}
