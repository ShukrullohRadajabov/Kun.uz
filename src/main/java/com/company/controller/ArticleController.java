package com.company.controller;

import com.company.dto.article.ArticleListDTO;
import com.company.dto.article.ArticleRequestDTO;
import com.company.dto.article.ArticleShortInfoDTO;
import com.company.dto.jwt.JwtDTO;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.ArticleStatus;
import com.company.enums.ProfileRole;
import com.company.exceptions.MethodNotAllowedException;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/private")
    public ResponseEntity<ArticleRequestDTO> create(@RequestBody @Valid ArticleRequestDTO dto,
                                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.MODERATOR);
        Integer prtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(articleService.create(dto, prtId));
    }



    @PostMapping("/private/{id}")
    public ResponseEntity<ArticleRequestDTO> update(@RequestBody ArticleRequestDTO dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/private/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/private/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtUtil(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id, jwt.getId()));
    }

    @GetMapping("/public/type/{id}/five")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast5ByTypeId2(id));
    }


    @GetMapping("/public/type/{id}/three")
    public ResponseEntity<List<ArticleShortInfoDTO>> get3ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast3ByTypeId(id));
    }

    @GetMapping("/public/get-last8")
    public ResponseEntity<List<ArticleShortInfoDTO>> get3ByTypeId2(@RequestBody ArticleListDTO listDTO) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
        return ResponseEntity.ok(articleService.getLast8WithoutList(listDTO.getList()));
    }

    @GetMapping("/public/get-id-lang")
    public ResponseEntity<?> getByIdAndLang(@RequestParam("id") String id, @RequestParam("language") String language) {
        return ResponseEntity.ok(articleService.getByIdAndLanguage(id, language));
    }

    @GetMapping("/public/get-last4")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4Except(@RequestParam("id") String id){
        return ResponseEntity.ok((articleService.getLast4ExceptGivenId(id)));
    }

    @GetMapping("/public/get-last4view")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4MostView(){
        return ResponseEntity.ok((articleService.getLast4MostView()));
    }


    @GetMapping("/public/get-last4tag")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4ByTag(@RequestParam("tag") Integer tag){
        return ResponseEntity.ok((articleService.getLast4ByTag(tag)));
    }

    @GetMapping("/public/get5by-type-region")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByTypeAndRegion(@RequestParam("type") Integer typeId, @RequestParam("region") Integer regionId){
        return ResponseEntity.ok((articleService.get5ByTypeAndRegion(typeId, regionId)));
    }

    @GetMapping("/public/get5by-category")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByCategory(@RequestParam("category") Integer categoryId){
        return ResponseEntity.ok(articleService.get5ByCategory(categoryId));
    }

    @GetMapping(value = "/public/paging")
    public ResponseEntity<Page<ArticleShortInfoDTO>> paging(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "30") int size,
                                                   @RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(articleService.getArticleByRegionIdPaging(page, size, id));
    }

    @GetMapping(value = "/public/paging-category")
    public ResponseEntity<Page<ArticleShortInfoDTO>> pagingWithCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "30") int size,
                                                            @RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(articleService.getArticleByCategoryIdPaging(page, size, id));
    }

    @GetMapping("/public/view-count")
    public ResponseEntity<Integer> increaseViewCount(@RequestParam("id") String articleId){
        return ResponseEntity.ok(articleService.increaseViewCount(articleId));
    }

    @GetMapping("/public/share-count")
    public ResponseEntity<Integer> increaseShareCount(@RequestParam("id") String articleId){
        return ResponseEntity.ok(articleService.increaseShareCount(articleId));
    }


}
