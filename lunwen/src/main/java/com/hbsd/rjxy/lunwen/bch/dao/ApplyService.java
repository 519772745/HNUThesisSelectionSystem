package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ApplyService {


    /**
     * @param subId
     * @param pageNum
     * @param pageLimit
     * @return
     */
    Page<Student> getStuWithSub(Integer subId, Integer pageNum, Integer pageLimit);

    /**
     *
     * @param student
     */
    void updateStu(Student student);

    /**
     *
     * @param id
     * @return
     */
    Student get(String id);

}