package com.company.service;

import com.company.entity.ArticleLikeEntity;
import com.company.enums.EmotionStatus;
import com.company.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public boolean like(String articleId, Integer profileId) {
        makeEmotion(articleId, profileId, EmotionStatus.LIKE);
        return true;
    }

    public boolean dislike(String articleId, Integer profileId) {
        makeEmotion(articleId, profileId, EmotionStatus.DISLIKE);
        return true;
    }

    public boolean delete(String articleId, Integer profileId) {
        articleLikeRepository.delete(articleId, profileId);
        return true;
    }


    private void makeEmotion(String articleId, Integer profileId, EmotionStatus status) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository
                .findByArticleIdAndProfileId(articleId, profileId);
        if (optional.isEmpty()) {
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            articleLikeRepository.save(entity);
            // article like count dislike count larni trigger orqali qilasizlar.
        } else {
            ArticleLikeEntity articleLikeEntity = optional.get();
            articleLikeRepository.update(status, articleId, profileId);
            if (articleLikeEntity.getStatus().equals(EmotionStatus.LIKE)){
                delete(articleId, profileId);
            }
            else if (articleLikeEntity.getStatus().equals(EmotionStatus.DISLIKE)){
                delete(articleId, profileId);
            }
        }
    }
}
