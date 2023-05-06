package com.company.controller;

import com.company.dto.comment.CommentRequestDTO;
import com.company.dto.comment.CommentUpdateRequestDTO;
import com.company.dto.jwt.JwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping({"", "/"})
    public ResponseEntity<CommentRequestDTO> create(@RequestBody CommentRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                         @RequestBody CommentUpdateRequestDTO dto,
                                         @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.update(id, dto, jwtDTO.getId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("id") Integer id,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
        return ResponseEntity.ok(commentService.delete(id));
    }

//    @GetMapping(value = "/get-saved-articles")
//    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorization) {
//        JwtDTO jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN,
//                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
//        List<SavedArticleResponseDTO> list = commentService.getAll(jwtDTO.getId());
//        return ResponseEntity.ok(list);
//    }
}
