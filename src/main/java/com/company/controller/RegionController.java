package com.company.controller;

import com.company.dto.jwt.JwtDTO;
import com.company.dto.region.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/private")
    public ResponseEntity<RegionDTO> create(@RequestBody @Valid RegionDTO dto,
                                            HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping("/private/update/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto,
                                            HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request);
        return ResponseEntity.ok(regionService.deleteProfile(id));
    }


    @GetMapping(value = "/private/list")
    public ResponseEntity<List<RegionDTO>> getList(){
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
