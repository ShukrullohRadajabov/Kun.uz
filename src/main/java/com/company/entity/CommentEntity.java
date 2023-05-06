package com.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Table(name = "comment")
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "prifile_id")
    private Integer profileId;
    @Column(name = "content", columnDefinition = "text")
    private String content;
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
    @Column(name = "replay_id")
    private Integer replayId;
    @Column(name = "visible")
    private Boolean visible = true;
}
