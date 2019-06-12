package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Student, Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update tbl_student stu  set " +
            "stu.subject_state = CASE WHEN :#{#stu.subState} IS NULL THEN stu.subject_state ELSE :#{#stu.subState} END " +
            "where stu.s_id = :#{#stu.sId}",nativeQuery = true)
    @Transactional(readOnly = false)
    void updateSub(@Param("stu") Student student);

    @Transactional(readOnly = false)
    @Query(value = "select * " +
            "from tbl_student " +
            "where s_id = :#{#id}",nativeQuery = true)
    List<Student> findById(@Param("id") String id);
}
