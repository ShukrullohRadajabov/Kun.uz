package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private String name;
    private String nameUZ;
    private String nameRU;
    private String nameEN;
    private Boolean visible;
    private LocalDateTime createdDate;
}
