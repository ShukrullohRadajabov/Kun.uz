package com.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "comment_like")
@Entity
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "prifile_id")
    private Integer profileId;
    @Column(name = "comment_id")
    private Integer commentId;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "status")
    private LocalDateTime status;
}
