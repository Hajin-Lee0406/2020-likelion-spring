package com.example.demo.post.controller;

import com.example.demo.post.dto.CommentDto;
import com.example.demo.post.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public String write(@PathVariable("id") Long id, CommentDto commentDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        commentDto.setWriter(username);
        commentService.saveComment(id, commentDto);
        return "redirect:/post/{id}";
    }

    @PutMapping("/comment/{board_id}/{comment_id}")
    @ResponseBody
    public Long update(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id, CommentDto commentDto){
        return commentService.update(board_id, comment_id, commentDto);
    }

    @DeleteMapping("/comment/{board_id}/{comment_id}")
    public String delete(@PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id){
        commentService.delete(board_id, comment_id);
        return "redirect:/post/{board_id}";
    }
}
