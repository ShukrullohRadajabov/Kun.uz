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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping({"", "/"})
    public ResponseEntity<CommentRequestDTO> create(@RequestBody CommentRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CommentUpdateRequestDTO dto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentService.update(id, dto, jwtDTO.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentService.delete(id, jwtDTO.getId()));
    }

    @GetMapping(value = "/list/{id}")
    public ResponseEntity<?> getList(@PathVariable("id")String articleId) {
        return ResponseEntity.ok(commentService.getCommentList(articleId));
    }

    @GetMapping(value = "/list-all-paging")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO dto = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.getAll(page, size));
    }


//    @GetMapping(value = "/get-saved-articles")
//    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorization) {
//        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN,
//                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
//        List<SavedArticleResponseDTO> list = commentService.getAll(jwtDTO.getId());
//        return ResponseEntity.ok(list);
//    }
}
