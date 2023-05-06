package com.company.service;
import com.company.dto.article.ArticleShortInfoDTO;
import com.company.dto.saved.SavedArticleRequestDTO;
import com.company.dto.saved.SavedArticleResponseDTO;
import com.company.entity.SavedArticleEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.SavedArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    private final ArticleService articleService;

    public String create(String articleId, Integer ownerId) {
        SavedArticleRequestDTO dto = new SavedArticleRequestDTO();
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(articleId);
        entity.setOwnerId(ownerId);
        entity.setCreatedDate(LocalDateTime.now());
        savedArticleRepository.save(entity);
        return "Successfully create";
    }
    public Boolean delete(String articleId, Integer ownerId) {
//        Optional<SavedArticleEntity> optional = savedArticleRepository.findAllByArticleIdAndOwnerId(articleId, ownerId);
//        if(optional.isEmpty()){
//            throw new ItemNotFoundException("Item not found");
//        }
//        savedArticleRepository.deleteSavedArticle(articleId, ownerId);
        int i = savedArticleRepository.deleteSavedArticle(articleId, ownerId);
        return true;
    }

    public List<SavedArticleResponseDTO> getAll(Integer ownerId) {
        List<SavedArticleEntity> all = savedArticleRepository.getAll(ownerId);
        List<SavedArticleResponseDTO> dtos = new ArrayList<>();
        all.forEach(savedArticleEntity -> {
            dtos.add(toDTO(savedArticleEntity));
        });
        return dtos;
    }

    private SavedArticleResponseDTO toDTO(SavedArticleEntity entity){
        SavedArticleResponseDTO dto = new SavedArticleResponseDTO();
        ArticleShortInfoDTO info = articleService.toArticleShortInfo(entity.getArticle());
        dto.setId(entity.getId());
        dto.setArticleShortInfoDTO(info);
        return dto;
    }
}
