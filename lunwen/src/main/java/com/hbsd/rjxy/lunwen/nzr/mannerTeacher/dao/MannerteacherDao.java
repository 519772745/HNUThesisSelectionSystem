package com.hbsd.rjxy.lunwen.nzr.mannerTeacher.dao;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import org.hibernate.Session;


import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MannerteacherDao extends JpaRepository<Teacher,String> {


}
