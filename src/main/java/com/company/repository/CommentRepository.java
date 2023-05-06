package com.company.repository;


import com.company.dto.article.ArticleCommentDTO;
import com.company.entity.CommentEntity;
import com.company.mapper.ArticleCommentMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false where id =:id")
    int deleteSavedArticle(@Param("id") Integer id);


    @Transactional
    @Modifying
    @Query(value = "select c.id, c.created_date, c.update_date, p.id, p.name, p.surname from comment as c inner join profile as p on p.id = c.profile_id where c.article_id =:article_id", nativeQuery = true)
    List<ArticleCommentMapper> listCommentById(@Param("article_id") String articleId);

    List<CommentEntity> findAllByArticleId(String articleId);

    Page<CommentEntity> findAll(Pageable paging);
}
