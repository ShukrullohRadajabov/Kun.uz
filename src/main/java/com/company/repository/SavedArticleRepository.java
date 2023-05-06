package com.company.repository;

import com.company.entity.SavedArticleEntity;
import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends
        CrudRepository<SavedArticleEntity, Integer> {

//    @Query("from SavedArticleEntity where articleId =:articleId and ownerId =:ownerId")
//    int deleteSavedArticle(@Param("articleId") String articleId, @Param("ownerId") Integer ownerId);

//    Optional<SavedArticleEntity> findAllByArticleIdAndOwnerId(String articleId, Integer ownerId);

    @Query("delete from SavedArticleEntity where articleId =:articleId and ownerId =:ownerId")
    int deleteSavedArticle(@Param("articleId") String articleId, @Param("ownerId") Integer ownerId);

//    @Query("from SavedArticleEntity where ownerId =:ownerId")
    List<SavedArticleEntity> getAll(@Param("ownerId") Integer ownerId);
}
