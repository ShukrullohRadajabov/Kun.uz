package com.company.repository;

import com.company.entity.ArticleLikeEntity;
import com.company.enums.EmotionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Modifying
    @Transactional
    @Query("update ArticleLikeEntity set status =:status where articleId =:articleId and profileId =:profileId")
    int update(@Param("status") EmotionStatus status,
               @Param("articleId") String articleId,
               @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("delete from ArticleLikeEntity where articleId =:articleId and profileId =:profileId")
    int delete(@Param("articleId") String articleId, @Param("profileId")Integer profileId);

}
