package com.hbsd.rjxy.lunwen.lcl.service;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.lcl.util.PageBean;

import java.util.List;

public interface StudentService {
    //获取所有学生，返回list
    public List<Student> findAll();
    //获得int类型学生总数
    public int totalCount();
    //通过学号查询
    public Student findByID(String sID);
    //通过学号删除
    public void delete(String sID);

    //分页对象
    public PageBean<Student> findPageBean(int pageNum, int pageSize);
}
