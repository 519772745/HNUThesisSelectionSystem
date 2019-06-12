package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuWithSubRepository extends JpaRepository<StudentWithSubject, Integer> {

}
