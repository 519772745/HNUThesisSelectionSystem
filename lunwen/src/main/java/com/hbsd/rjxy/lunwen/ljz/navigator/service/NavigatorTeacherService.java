package com.hbsd.rjxy.lunwen.ljz.navigator.service;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.ljz.navigator.dao.NavigatorTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NavigatorTeacherService {
    @Autowired
    private NavigatorTeacherRepository navigatorTeacherRepository;
    public Teacher findTeacherByTId(String tId){
        if(tId!=null){
            Optional<Teacher>optional= navigatorTeacherRepository.findById(tId);
            return optional.get();
        }
        return null;
    }
}
