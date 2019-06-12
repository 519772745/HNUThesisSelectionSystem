package com.hbsd.rjxy.lunwen;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.ljz.login.dao.LoginTeacherRepository;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginMajorService;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginStudentService;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginTeacherService;
import com.hbsd.rjxy.lunwen.ljz.navigator.dao.NavigatorSubjectRepository;
import com.hbsd.rjxy.lunwen.ljz.submit.dao.SubmitStudentRepository;
import com.hbsd.rjxy.lunwen.ljz.submit.dao.SubmitSwsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LunwenApplicationTests {
    @Autowired
    private LoginStudentService loginStudentService;
    @Autowired
    private LoginMajorService loginMajorService;
    @Autowired
    private LoginTeacherService loginTeacherService;
    @Autowired
    private LoginTeacherRepository loginTeacherRepository;
    @Autowired
    private NavigatorSubjectRepository navigatorSubjectRepository;
    @Autowired
    private SubmitSwsRepository submitSwsRepository;
    @Autowired
    private SubmitStudentRepository submitStudentRepository;
    @Test
    //@Transactional(readOnly = false)
    //@Rollback(false)
    public void contextLoads() {
        //submitStudentRepository.deleteById("2016011493");
        System.out.println("事务开启");
        try {
            submitSwsRepository.deleteById(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("事务结束");

    }

}
