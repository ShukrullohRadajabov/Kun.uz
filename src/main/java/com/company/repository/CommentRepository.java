package com.company.repository;


import com.company.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false where id =:id")
    int  deleteSavedArticle(@Param("id") Integer id);
}
