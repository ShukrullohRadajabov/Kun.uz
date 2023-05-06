package com.company.service;

import com.company.dto.comment.CommentRequestDTO;
import com.company.dto.comment.CommentUpdateRequestDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.tag.TagRequestDTO;
import com.company.entity.CommentEntity;
import com.company.entity.TagEntity;
import com.company.enums.ProfileRole;
import com.company.exceptions.AppBadRequestException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentRequestDTO create(CommentRequestDTO dto, Integer ownerId) {
        CommentEntity entity = new CommentEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setContent(dto.getContent());
        entity.setProfileId(ownerId);
        entity.setReplayId(dto.getReplyId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        commentRepository.save(entity);
        return dto;
    }
    public Boolean delete(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Comment is empty");
        }
        CommentEntity entity = optional.get();
        commentRepository.deleteSavedArticle(entity.getId());
        return true;
    }

//    public List<SavedArticleResponseDTO> getAll(Integer ownerId) {
////        List<SavedArticleEntity> all = commentRepository.getAll(ownerId);
//        List<SavedArticleResponseDTO> dtos = new ArrayList<>();
//        all.forEach(savedArticleEntity -> {
//            dtos.add(toDTO(savedArticleEntity));
//        });
//        return dtos;
//    }


    public Boolean update(Integer id, CommentUpdateRequestDTO dto, Integer adminId) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Comment is empty");
        }
        CommentEntity entity = optional.get();
        entity.setContent(dto.getContent());
        entity.setProfileId(adminId);
        entity.setArticleId(dto.getArticleId());
        commentRepository.save(entity);
        return true;
    }

}
