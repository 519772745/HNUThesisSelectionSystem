package com.hbsd.rjxy.lunwen.lcl.dao;

import com.hbsd.rjxy.lunwen.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student,String> {
}
