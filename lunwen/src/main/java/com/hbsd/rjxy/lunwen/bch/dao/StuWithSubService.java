package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import org.springframework.stereotype.Service;

@Service
public interface StuWithSubService {

    /**
     * 保存 StudentWithSubject 表
     * @param studentWithSubject
     */
    void savaStuWithSub(StudentWithSubject studentWithSubject);
}
