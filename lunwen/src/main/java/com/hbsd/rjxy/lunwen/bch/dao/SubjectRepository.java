package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional(readOnly = false)
    @Query(value = "update tbl_subject sub  set " +
                   "sub.sub_name = CASE WHEN :#{#subject.subName} IS NULL THEN sub.sub_name ELSE :#{#subject.subName} END ," +
                   "sub.sub_count = CASE WHEN :#{#subject.subCount} IS NULL THEN sub.sub_count ELSE :#{#subject.subCount} END ," +
                   "sub.path = CASE WHEN :#{#subject.docPath} IS NULL THEN sub.sub_count ELSE :#{#subject.docPath} END " +
                   "where sub.sub_id = :#{#subject.subId}",nativeQuery = true)
    void update(@Param("subject") Subject subject);

    @Modifying(clearAutomatically = true)
    @Transactional(readOnly = false)
    @Query(value = "update tbl_subject sub  set " +
            "sub.remain = CASE WHEN :#{#subject.remain} IS NULL THEN sub.remain ELSE :#{#subject.remain} END " +
            "where sub.sub_id = :#{#subject.subId}",nativeQuery = true)
    void selectToUpdate(@Param("subject") Subject subject);

    @Transactional(readOnly = true)
    List<Subject> findBySubName(String subName);

    @Transactional(readOnly = false)
    @Query(value = "select * " +
            "from tbl_subject " +
            "where t_id = :#{#tid}",nativeQuery = true)
    Page<Subject> selectAllByTId(@Param("tid") String tid, Pageable pageable);
    
}