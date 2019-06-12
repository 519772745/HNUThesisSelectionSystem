package com.hbsd.rjxy.lunwen.bch.service;

import com.hbsd.rjxy.lunwen.bch.dao.StuWithSubRepository;
import com.hbsd.rjxy.lunwen.bch.dao.StuWithSubService;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuWithSubServiceImpl implements StuWithSubService {

    @Autowired
    private StuWithSubRepository stuWithSubRepository;

    @Override
    public void savaStuWithSub(StudentWithSubject studentWithSubject) {
        stuWithSubRepository.save(studentWithSubject);
    }
}
