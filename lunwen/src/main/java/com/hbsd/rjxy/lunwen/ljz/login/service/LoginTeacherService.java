package com.hbsd.rjxy.lunwen.ljz.login.service;

import com.hbsd.rjxy.lunwen.entity.Major;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.ljz.login.dao.LoginTeacherRepository;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoginTeacherService {
    @Autowired
    private LoginTeacherRepository loginTeacherRepository;
    public Teacher findTeacherByTId(String tId){
        if(tId!=null){
            return loginTeacherRepository.findById(tId).get();
        }
        return null;
    }
    @Transactional
    public void updateTeacher(Teacher teacher){
        if(teacher!=null){
            loginTeacherRepository.saveAndFlush(teacher);
        }
        return;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return{
     *      -0不属于选题老师的范围
     *      -1登录成功+teacher对象
     *      -2密码错误
     * }
     */
    public String login(String username,String password){
        JSONObject jsonObject=new JSONObject();
        if(loginTeacherRepository.existsById(username)){
            Teacher teacher=new Teacher();
            teacher.settId(username);
            Example<Teacher>example=Example.of(teacher);
            Optional<Teacher>result= loginTeacherRepository.findOne(example);
            if(result.get().gettPwd().equals(password)){
                //密码正确
                jsonObject.put("state",1);
                JsonConfig jsonConfig=new JsonConfig();
                jsonConfig.setCycleDetectionStrategy(
                        CycleDetectionStrategy.LENIENT
                );
                JSONObject teacherJson=JSONObject.fromObject(result.get(),jsonConfig);
                jsonObject.put("teacher",teacherJson);
            }else{
                //密码错误
                jsonObject.put("state",2);
            }
        }else{
            jsonObject.put("state",0);
        }
        return jsonObject.toString();
    }
    public List<Teacher>findTeachersOfMajor(Major major){
        Teacher teacher=new Teacher();
        teacher.setMajor(major);
        Example<Teacher>example=Example.of(teacher);
        List<Teacher>teacherList= loginTeacherRepository.findAll(example);
        if(teacherList!=null){
            return teacherList;
        }
        return null;
    }

    public List<Teacher> findAll() {
        Teacher teacher=new Teacher();
        Example<Teacher>example=Example.of(teacher);
        List<Teacher>teacherList= loginTeacherRepository.findAll(example);
        if (teacherList!=null){
            return  teacherList;
        }
        return null;
    }
}
