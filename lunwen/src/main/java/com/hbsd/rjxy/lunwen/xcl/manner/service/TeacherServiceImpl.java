package com.hbsd.rjxy.lunwen.xcl.manner.service;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.xcl.manner.dao.TeacherRePository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherRePository teacherRePository;
    @Override
    public List<Teacher> findAll(){
        List<Teacher> list= teacherRePository.findAll();
        return list;
    }
}
