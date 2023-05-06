package com.company.service;

import com.company.dto.article.ArticleShortInfoDTO2;
import com.company.dto.profile.ProfileShortInfoDTO;
import com.company.dto.comment.CommentRequestDTO;
import com.company.dto.comment.CommentResponseDTO;
import com.company.dto.comment.CommentUpdateRequestDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.mapper.ArticleCommentMapper;
import com.company.repository.ArticleRepository;
import com.company.repository.CommentRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;

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

    public Boolean delete(Integer id, Integer ownerId) {
        Optional<ProfileEntity> optional = profileRepository.findById(ownerId);
        Optional<CommentEntity> optional1 = commentRepository.findById(id);
        if (optional1.isEmpty()) {
            throw new AppBadRequestException("Comment is empty");
        }
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Method not allowed");
        }
        ProfileEntity entity = optional.get();
        if (entity.getId().equals(ownerId) || entity.getStatus().equals(ProfileRole.ADMIN)) {
            int i = commentRepository.deleteSavedArticle(id);
            System.out.println(i);
        }
        return true;
    }

    public List<CommentResponseDTO> getCommentList(String articleId) {
        List<ArticleCommentMapper> mapper = commentRepository.listCommentById(articleId);
        if (mapper.isEmpty()){
            throw new ItemNotFoundException("Item not found");
        }
        List<CommentResponseDTO> list = new ArrayList<>();
        mapper.forEach(mapper1 -> {
            list.add(toArticleShortDTO(mapper1));
        });
        return list;
    }
/*

    public List<CommentResponseDTO> toShortDTO(List<CommentEntity> entities){
        List<CommentResponseDTO> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdateDate());
            dto.setProfile(profileService.toShortInfo(entity.getProfile()));
            dtos.add(dto);
        });
        return dtos;
    }
*/

    private CommentResponseDTO toArticleShortDTO(ArticleCommentMapper mapper){
        Optional<ProfileEntity> profile = profileRepository.findById(mapper.id());
        if(profile.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        ProfileEntity profileEntity = new ProfileEntity();
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(mapper.id());
        dto.setCreatedDate(mapper.createdDate());
        dto.setUpdateDate(mapper.updatedDate());
        dto.setProfile(new ProfileShortInfoDTO(profileEntity.getId(), profileEntity.getName(), profileEntity.getSurname()));
        return dto;
    }

    public List<CommentResponseDTO> getAll(Integer adminId) {
        Iterable<CommentEntity> all = commentRepository.findAll();
        List<CommentResponseDTO> list = new LinkedList<>();
        all.forEach(commentEntity -> {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setContent(commentEntity.getContent());
            dto.setCreatedDate(commentEntity.getCreatedDate());
            list.add(dto);
        });
        return list;
    }

    public Page<CommentResponseDTO> getAll(int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<CommentEntity> pageObj = commentRepository.findAll(paging);

        Long totalCount = pageObj.getTotalElements();

        List<CommentEntity> entityList = pageObj.getContent();
        List<CommentResponseDTO> list = new LinkedList<>();
        entityList.forEach(entity -> {
            list.add(toArticleShortInfo(entity));
        });
        Page<CommentResponseDTO> response = new PageImpl<CommentResponseDTO>(list, paging, totalCount);
        return response;
    }


    public CommentResponseDTO toArticleShortInfo(CommentEntity entity) {
        Optional<ProfileEntity> profile = profileRepository.findById(entity.getProfileId());
        Optional<ArticleEntity> article = articleRepository.findById(entity.getArticleId());

        if (profile.isEmpty()) {
            throw new ItemNotFoundException("Profile Not found");
        }
        ProfileEntity profileEntity = profile.get();
        if (article.isEmpty()) {
            throw new ItemNotFoundException("Article Not found");
        }
        ArticleEntity articleEntity = article.get();
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(entity.getId());
        dto.setProfile(new ProfileShortInfoDTO(profileEntity.getId(), profileEntity.getName(), profileEntity.getSurname()));
        dto.setArticle(new ArticleShortInfoDTO2(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getDescription(), articleEntity.getPublishedDate()));
        dto.setReplyId(entity.getReplayId());
        return dto;
    }


//    public List<SavedArticleResponseDTO> getAll(Integer ownerId) {
////        List<SavedArticleEntity> all = commentRepository.getAll(ownerId);
//        List<SavedArticleResponseDTO> dtos = new ArrayList<>();
//        all.forEach(savedArticleEntity -> {
//            dtos.add(toDTO(savedArticleEntity));
//        });
//        return dtos;
//    }


}
