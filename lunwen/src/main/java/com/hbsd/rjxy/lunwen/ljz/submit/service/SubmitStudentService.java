package com.hbsd.rjxy.lunwen.ljz.submit.service;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.ljz.submit.dao.SubmitStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SubmitStudentService {
    @Autowired
    private SubmitStudentRepository submitStudentRepository;
    @Transactional
    public void updateStudent(Student student){
        if(student!=null){
            submitStudentRepository.saveAndFlush(student);
        }
        return;
    }
    public Student findStudentBySId(String sId){
        if(sId!=null){
            Optional<Student> option=submitStudentRepository.findById(sId);
            return option.get();
        }
        return null;
    }
}
