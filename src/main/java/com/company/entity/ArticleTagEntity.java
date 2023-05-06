package com.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "article_tag")
@Entity
public class ArticleTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tag_id")
    private Integer tagId;
    @Column(name = "article_id")
    private Integer articleId;

}


