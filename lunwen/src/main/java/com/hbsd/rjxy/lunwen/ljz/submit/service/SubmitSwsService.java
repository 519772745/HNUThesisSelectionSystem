package com.hbsd.rjxy.lunwen.ljz.submit.service;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.ljz.submit.dao.SubmitSwsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmitSwsService {
    @Autowired
    private SubmitSwsRepository submitSwsRepository;
    public List<StudentWithSubject>findSwsListByStudent(Student student){
        if(student!=null){
            StudentWithSubject sws=new StudentWithSubject();
            sws.setStudent(student);
            Example<StudentWithSubject>example=Example.of(sws);
            List<StudentWithSubject>temp=submitSwsRepository.findAll(example);
            return temp;
        }
        return null;
    }
    @Transactional(readOnly = false)
    public void deleteRecord(List<StudentWithSubject>studentWithSubjects){
        if(studentWithSubjects!=null&&studentWithSubjects.size()>0){
            System.out.println("清空学生选题记录");
            for (StudentWithSubject sws:
                    studentWithSubjects) {
                System.out.println("删除"+sws.getId()+sws.getSubject().getSubName());
                submitSwsRepository.deleteSwsById(sws.getId());

            }
            //submitSwsRepository.deleteAll(studentWithSubjects);
        }
        return;
    }
    @Transactional
    public void saveRecord(StudentWithSubject studentWithSubject){
        if(studentWithSubject!=null){
            submitSwsRepository.save(studentWithSubject);
        }
        return;
    }
}
