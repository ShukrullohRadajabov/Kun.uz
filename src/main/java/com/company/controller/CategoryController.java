package com.company.controller;

import com.company.dto.category.CategoryDTO;
import com.company.dto.jwt.JwtDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid CategoryDTO dto,
                                              @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.update(id, dto));
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
        return ResponseEntity.ok(categoryService.deleteProfile(id));
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<CategoryDTO>> getList(@RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        List<CategoryDTO> response = categoryService.getList();
        return ResponseEntity.ok(response);
    }

  /*  @GetMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<CategoryDTO>> getByLang(@PathVariable("lang") String lang) {
        List<CategoryDTO> response = categoryService.getByLang(lang);
        return ResponseEntity.ok(response);
    }*/

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang) {
        List<String> list = categoryService.getByLang(lang);
        return ResponseEntity.ok(list);
    }
}
