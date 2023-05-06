package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.service.ArticleLikeService;
import com.company.service.CommentLikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") String commentId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.like(commentId, jwt.getId()));
    }


    @GetMapping("/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") String commentId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.dislike(commentId, jwt.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String commentId,
                                           @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.delete(commentId, jwt.getId()));
    }
}
