package com.company.dto.article;

import com.company.dto.category.CategoryResponseDTO;
import com.company.dto.region.RegionResponseDTO;
import com.company.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount = 0;
    private RegionResponseDTO region;
    private CategoryResponseDTO category;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private Integer likeCount;

}
