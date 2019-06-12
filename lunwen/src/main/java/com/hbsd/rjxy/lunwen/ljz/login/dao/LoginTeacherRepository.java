package com.hbsd.rjxy.lunwen.ljz.login.dao;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginTeacherRepository extends JpaRepository<Teacher,String> {

}
