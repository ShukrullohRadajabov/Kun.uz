package com.company.dto.article;

import com.company.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO2 {
    private String id;
    private String title;
    private String description;
    private LocalDateTime publishedDate;

    public ArticleShortInfoDTO2(String id, String title, String description, LocalDateTime publishedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
    }
}
