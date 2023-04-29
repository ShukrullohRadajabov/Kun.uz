package com.company.repository;

import com.company.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Transactional
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    @Query("SELECT nameUZ FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRU FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEN FROM CategoryEntity ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();
}
