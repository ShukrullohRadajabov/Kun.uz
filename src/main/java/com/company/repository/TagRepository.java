package com.company.repository;

import com.company.entity.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update TagEntity set visible = false, prtId =:prtId where id =:id")
    int updateTag(@Param("id") Integer id,@Param("prtId") Integer prtId);
}
