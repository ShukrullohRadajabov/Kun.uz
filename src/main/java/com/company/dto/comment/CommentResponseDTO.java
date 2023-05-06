package com.company.dto.comment;

import com.company.dto.article.ArticleShortInfoDTO2;
import com.company.dto.profile.ProfileShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponseDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private ProfileShortInfoDTO profile;
    private String content;
    private ArticleShortInfoDTO2 article;
    private Integer replyId;
}
