package com.company.controller;

import com.company.dto.article.ArticleCommentDTO;
import com.company.dto.comment.CommentRequestDTO;
import com.company.dto.comment.CommentUpdateRequestDTO;
import com.company.dto.jwt.JwtDTO;
import com.company.dto.region.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.mapper.ArticleCommentMapper;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping({"/public"})
    public ResponseEntity<CommentRequestDTO> create(@RequestBody CommentRequestDTO dto,
                                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER, ProfileRole.PUBLISHER);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentService.create(dto, prtId));
    }

    @PutMapping("public/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CommentUpdateRequestDTO dto,
                                          HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentService.update(id, dto, prtId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(commentService.delete(id, prtId));
    }

    @GetMapping(value = "/public/list/{id}")
    public ResponseEntity<?> getList(@PathVariable("id")String articleId) {
        return ResponseEntity.ok(commentService.getCommentList(articleId));
    }

    @GetMapping(value = "/private/list-all-paging")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.getAll(page, size));
    }
}
