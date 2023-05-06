package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.tag.TagRequestDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.TagService;
import com.company.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("")
    public ResponseEntity<TagDTO> create(@RequestBody TagRequestDTO dto,
                                         @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.create(dto, jwtDTO.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TagDTO> update(@PathVariable("id") Integer id, @RequestBody TagRequestDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(tagService.update(id, dto, jwtDTO.getId()));
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
        return ResponseEntity.ok(tagService.delete(id, jwtDTO.getId()));
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<TagDTO>> getList(@RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        List<TagDTO> response = tagService.getList();
        return ResponseEntity.ok(response);
    }

}
