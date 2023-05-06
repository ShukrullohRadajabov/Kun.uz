package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.service.ArticleLikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article_like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") String articleId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.like(articleId, jwt.getId()));
    }


    @GetMapping("/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") String articleId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.dislike(articleId, jwt.getId()));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String articleId,
                                           @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.delete(articleId, jwt.getId()));
    }
}
