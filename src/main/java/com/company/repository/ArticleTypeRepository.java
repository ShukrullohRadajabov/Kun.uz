package com.company.repository;

import com.company.entity.ArticleTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Transactional
public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {
    @Query("SELECT nameUZ FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    @Query("SELECT nameRU FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    @Query("SELECT nameEN FROM ArticleTypeEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();
}
