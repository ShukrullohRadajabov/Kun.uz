package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.dto.saved.SavedArticleRequestDTO;
import com.company.dto.saved.SavedArticleResponseDTO;
import com.company.enums.ProfileRole;
import com.company.service.SavedArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam("articleId") String articleId,
                                                         @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(savedArticleService.create(articleId, jwtDTO.getId()));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("articleId") String articleId,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
        return ResponseEntity.ok(savedArticleService.delete(articleId, jwtDTO.getId()));
    }

    @GetMapping(value = "/get-saved-articles")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN,
                ProfileRole.MODERATOR, ProfileRole.USER,ProfileRole.PUBLISHER );
            List<SavedArticleResponseDTO> list = savedArticleService.getAll(jwtDTO.getId());
        return ResponseEntity.ok(list);
    }
}
