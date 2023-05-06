package com.company.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
public interface ArticleCommentMapper {
    Integer id();
    LocalDateTime createdDate();
    LocalDateTime updatedDate();
    Integer profileId();
    String profileName();
    String profileSurname();

}
