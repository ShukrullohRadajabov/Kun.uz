package com.company.repository;

import com.company.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Transactional
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

  /*  List<RegionEntity> findAllByNameUZ();

    List<RegionEntity> findAllByNameEN();

    List<RegionEntity> findAllByNameRU();*/

    @Query("SELECT nameUZ FROM RegionEntity where visible = true ORDER BY createdDate ASC ")
    List<String> findAllByNameUzOrderByCreatedDate();

    //List<String> findAllByNameRuOrderByCreatedDate(String nameRu);
    @Query("SELECT nameRU FROM RegionEntity where visible = true ORDER BY createdDate ASC ")
    List<String> findAllByNameRuOrderByCreatedDate();

    //List<String> findAllByNameEngOrderByCreatedDate(String nameEng);
    @Query("SELECT nameEN FROM RegionEntity where visible = true ORDER BY createdDate ASC ")
    List<String> findAllByNameEngOrderByCreatedDate();
}
