package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.enums.ArticleStatus;
import com.company.mapper.ArticleSimpleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    Page<ArticleEntity> findByVisible(Boolean visible, Pageable pageable);

    Optional<ArticleEntity> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") Integer id);

    public List<ArticleEntity> findTop5ByTypeIdAndStatus(Integer typeId, ArticleStatus status, Sort sort);

    /*@Query("SELECT new ArticleEntity(a.id,a.title,a.description,a.attach.id,a.publishedDate) " +
            " FROM ArticleEntity a  where  a.type.id =:typeId and status =:status order by createdDate desc")
    public List<ArticleEntity> getTypeId(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);*/


    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:typeId and status =:status order by created_date desc Limit 5", nativeQuery = true)
    public List<ArticleSimpleMapper> getTypeId(@Param("typeId") Integer typeId, @Param("status") String status);


    public Page<ArticleEntity> findAllByTypeId(Integer typeId, Pageable pageable);

    public Optional<ArticleEntity> findByIdAndStatus(Integer id, ArticleStatus status);
}
