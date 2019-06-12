package com.hbsd.rjxy.lunwen.lcl.service;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.lcl.dao.LclSwsRepository;
import com.hbsd.rjxy.lunwen.lcl.dao.StudentDao;
import com.hbsd.rjxy.lunwen.lcl.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService, Serializable {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private LclSwsRepository lclSwsRepository;

    @Override
    public List<Student> findAll() {
        List<Student> list = studentDao.findAll();
        return list;
    }

    @Override
    public int totalCount() {
        List<Student> list = studentDao.findAll();
        int num = list.size();
        return num;
    }

    @Override
    public Student findByID(String sID) {
        Student s = studentDao.getOne(sID);
        return s;
    }

    @Override
    @Transactional
    public void delete(String sID) {
        Student student = studentDao.getOne(sID);
        for (StudentWithSubject sws:
             student.getStudentWithSubjectList()) {

            lclSwsRepository.deleteSwsById(sws.getId());
        }
        studentDao.delete(student);
        return;
    }

    @Override
    public PageBean<Student> findPageBean(int pageNum, int pageSize) {
        int totalRecord = studentDao.findAll().size();
        PageBean pb = new PageBean(pageNum,pageSize,totalRecord);
        int startIndex = pb.getStartIndex();
        if (totalRecord >= 10 && (startIndex+pageSize)<= studentDao.findAll().size()){
            List<Student> list = studentDao.findAll().subList(startIndex,startIndex+pageSize);
            pb.setList(list);
            System.out.println(pb.getList().size()+"********************"+startIndex);
        }else {
            List<Student> list = studentDao.findAll().subList(startIndex,totalRecord);
            pb.setList(list);
            System.out.println(pb.getList().size()+"********************"+startIndex);
        }
        return pb;
    }
}
