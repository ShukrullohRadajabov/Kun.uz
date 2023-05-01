package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import com.company.mapper.ArticleShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Transactional
    @Modifying
    @Query("update  ArticleEntity  set status = :status where id =:id")
    int changeStatus(@Param("status") ArticleStatus status, @Param("id") String id);

//    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 5")
//    List<ArticleEntity> findLastFiveArticleByType(@Param("articleTypeId") Integer articleTypeId);

    List<ArticleEntity> findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query("From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
    List<ArticleEntity> find5ByTypeId(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);
//
//    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
//    List<ArticleEntity> find5ByTypeId2(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);

    List<ArticleEntity> findTop3ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit :limit",
            nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNative(@Param("t_id") Integer typeId,
                                              @Param("status") String status,
                                              @Param("limit") Integer limit);

    @Query("from ArticleEntity where status = :status and visible = true")
    List<ArticleEntity> getAllArticle(@Param("status") ArticleStatus published);
}
