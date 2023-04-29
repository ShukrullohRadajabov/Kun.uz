package com.company.controller;

import com.company.dto.JwtDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody @Valid RegionDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto,
                                            @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.update(id, dto));
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
        return ResponseEntity.ok(regionService.deleteProfile(id));
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<RegionDTO>> getList(@RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        List<RegionDTO> response = regionService.getList();
        return ResponseEntity.ok(response);
    }

  /*  @GetMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<RegionDTO>> getByLang(@PathVariable("lang") String lang) {
        List<RegionDTO> response = regionService.getByLang(lang);
        return ResponseEntity.ok(response);
    }*/

    @PutMapping(value = "/getByLang/{lang}")
    public ResponseEntity<List<String>> getByLang(@PathVariable("lang") String lang) {
        List<String> list = regionService.getByLang(lang);
        return ResponseEntity.ok(list);
    }

}
