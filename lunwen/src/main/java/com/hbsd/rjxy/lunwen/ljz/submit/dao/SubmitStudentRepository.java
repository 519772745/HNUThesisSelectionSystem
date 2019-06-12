package com.hbsd.rjxy.lunwen.ljz.submit.dao;

import com.hbsd.rjxy.lunwen.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitStudentRepository extends JpaRepository<Student,String> {
}
