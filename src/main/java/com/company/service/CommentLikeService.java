package com.company.service;

import com.company.entity.CommentLikeEntity;
import com.company.enums.EmotionStatus;
import com.company.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public boolean like(String commentId, Integer profileId) {
        makeEmotion(commentId, profileId, EmotionStatus.LIKE);
        return true;
    }

    public boolean dislike(String commentId, Integer profileId) {
        makeEmotion(commentId, profileId, EmotionStatus.DISLIKE);
        return true;
    }

    public boolean delete(String commentId, Integer profileId) {
        commentLikeRepository.delete(commentId, profileId);
        return true;
    }


    private void makeEmotion(String commentId, Integer profileId, EmotionStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository
                .findByCommentIdAndProfileId(commentId, profileId);
        if (optional.isEmpty()) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            commentLikeRepository.save(entity);
            // comment like count dislike count larni trigger orqali qilasizlar.
        } else {
            CommentLikeEntity commentLikeEntity = optional.get();
            commentLikeRepository.update(status, commentId, profileId);
            if (commentLikeEntity.getStatus().equals(EmotionStatus.LIKE)){
                delete(commentId, profileId);
            }
            else if (commentLikeEntity.getStatus().equals(EmotionStatus.DISLIKE)){
                delete(commentId, profileId);
            }
        }
    }
}
