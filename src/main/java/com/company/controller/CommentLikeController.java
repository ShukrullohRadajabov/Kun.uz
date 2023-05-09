package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleLikeService;
import com.company.service.CommentLikeService;
import com.company.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @GetMapping("private/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") String commentId,
                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentLikeService.like(commentId,prtId));
    }


    @GetMapping("private/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") String commentId,
                                           HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentLikeService.dislike(commentId,prtId));
    }

    @GetMapping("/private/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String commentId,
                                          HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentLikeService.delete(commentId,prtId));
    }
}
