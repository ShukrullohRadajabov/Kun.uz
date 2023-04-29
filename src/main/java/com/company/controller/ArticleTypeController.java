package com.company.controller;

import com.company.dto.JwtDTO;
import com.company.dto.ArticleTypeDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.ArticleTypeService;
import com.company.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody @Valid ArticleTypeDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleTypeDTO> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleTypeService.deleteProfile(id));
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<ArticleTypeDTO>> getList(@RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        List<ArticleTypeDTO> response = articleTypeService.getList();
        return ResponseEntity.ok(response);
    }

  /*  @GetMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@PathVariable("lang") String lang) {
        List<ArticleTypeDTO> response = articleTypeService.getByLang(lang);
        return ResponseEntity.ok(response);
    }*/

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang) {
        List<String> list = articleTypeService.getByLang(lang);
        return ResponseEntity.ok(list);
    }
}
