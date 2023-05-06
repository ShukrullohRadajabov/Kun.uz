package com.company.dto.saved;

import com.company.dto.article.ArticleShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedArticleResponseDTO {
    private Integer id;
    private ArticleShortInfoDTO articleShortInfoDTO;
}
