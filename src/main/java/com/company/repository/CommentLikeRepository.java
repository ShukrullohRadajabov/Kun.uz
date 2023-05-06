package com.company.repository;

import com.company.entity.CommentLikeEntity;
import com.company.entity.CommentLikeEntity;
import com.company.enums.EmotionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(String commentId,  Integer profileId);

    @Modifying
    @Transactional
    @Query("update CommentLikeEntity set status =:status where commentId =:commentId and profileId =:profileId")
    int update(@Param("status") EmotionStatus status,
               @Param("commentId") String commentId,
               @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("delete from CommentLikeEntity where commentId =:commentId and profileId =:profileId")
    int delete(@Param("commentId") String commentId, @Param("profileId")Integer profileId);

}
