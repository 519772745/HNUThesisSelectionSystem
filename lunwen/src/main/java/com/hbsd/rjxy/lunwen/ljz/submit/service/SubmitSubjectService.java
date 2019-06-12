package com.hbsd.rjxy.lunwen.ljz.submit.service;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.ljz.submit.dao.SubmitSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitSubjectService {
    @Autowired
    private SubmitSubjectRepository submitSubjectRepository;
    public Subject findSubjectBySubId(Integer subId){
        if(subId!=null){
            return submitSubjectRepository.findById(subId).get();
        }
        return null;
    }
}
