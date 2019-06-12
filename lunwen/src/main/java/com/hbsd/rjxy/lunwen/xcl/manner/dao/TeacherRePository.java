package com.hbsd.rjxy.lunwen.xcl.manner.dao;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeacherRePository extends JpaRepository<Teacher,Integer> {
}
