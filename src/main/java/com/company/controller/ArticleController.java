package com.company.controller;

import com.company.dto.article.ArticleListDTO;
import com.company.dto.article.ArticleRequestDTO;
import com.company.dto.article.ArticleShortInfoDTO;
import com.company.dto.jwt.JwtDTO;

import com.company.enums.ArticleStatus;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody @Valid ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDTO> update(@RequestBody ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id, jwt.getId()));
    }

    @GetMapping("/type/{id}/five")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast5ByTypeId2(id));
    }


    @GetMapping("/type/{id}/three")
    public ResponseEntity<List<ArticleShortInfoDTO>> get3ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast3ByTypeId(id));
    }

    @GetMapping("get-last8")
    public ResponseEntity<List<ArticleShortInfoDTO>> get3ByTypeId2(@RequestBody ArticleListDTO listDTO) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast8WithoutList(listDTO.getList()));
    }




}
