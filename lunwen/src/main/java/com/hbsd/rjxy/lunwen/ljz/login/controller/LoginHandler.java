package com.hbsd.rjxy.lunwen.ljz.login.controller;

import com.hbsd.rjxy.lunwen.entity.*;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginMajorService;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginStudentService;
import com.hbsd.rjxy.lunwen.ljz.login.service.LoginTeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginHandler {
    @Autowired
    private LoginStudentService loginStudentService;
    @Autowired
    private LoginTeacherService loginTeacherService;
    @Autowired
    private LoginMajorService loginMajorService;
    @PostMapping(value = "/append")
    public ModelAndView addRecordController(
                HttpSession session,
                @RequestParam("phone")String phone,
                @RequestParam("email")String email){
        Object user=session.getAttribute("user");
        ModelAndView mv=new ModelAndView();
        if(user instanceof Student){
            Student student=(Student)user;
            student.setsPhone(phone);
            student.setsEmail(email);
            session.setAttribute("user",student);
            loginStudentService.updateStudent(student);
            int collegeId=student.getMajor().getParentMId();
            //查看该学院有多少个方向
            List<Major>majorList= loginMajorService.findByCollegeId(collegeId);
            List<Teacher>teachers=new ArrayList<>();
            for (Major major:
                    majorList) {
                //此方向有多少老师
                List<Teacher>teacherList= loginTeacherService.findTeachersOfMajor(major);
                for (Teacher teacher:
                        teacherList) {
                    teachers.add(teacher);
                }
            }
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.setIgnoreDefaultExcludes(false);
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            JSONArray jsonArray=JSONArray.fromObject(teachers,jsonConfig);
            mv.addObject("teacherNav",jsonArray.toString());
            session.setAttribute("teacherNav",jsonArray.toString());
            mv.setViewName("student");
        }else if(user instanceof Teacher){
            /**
             * 有问题！！
             */
            Teacher example=(Teacher)user;
            Teacher teacher=loginTeacherService.findTeacherByTId(example.gettId());
            teacher.settPhone(phone);
            teacher.settEmail(email);
            session.setAttribute("user",teacher);
            Set<Subject> subjectList=teacher.getSubjectSet();

            loginTeacherService.updateTeacher(teacher);
            Set<Subject>subjects=teacher.getSubjectSet();
            JSONArray jsonArray=new JSONArray();
            for (Subject subject:
                    subjects) {
                JSONObject subObj=new JSONObject();
                subObj.put("subName",subject.getSubName());
                subObj.put("subId",subject.getSubId());
                subObj.put("max",subject.getSubCount());
                jsonArray.add(subObj);
            }
            mv.addObject("subList",jsonArray.toString());
            System.out.println(jsonArray.toString());
            mv.setViewName("teacher");
        }else{
            //管理员待拓展
        }
        return mv;
    }
    @PostMapping(value = "/login")
    public ModelAndView loginController(
            HttpSession session,
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            @RequestParam(value="role",required = false)String role){
        ModelAndView mv=new ModelAndView();
        if(role.equals("student")){
            int state= loginStudentService.login(username,password);
            /**
             * -0用户不在数据库中 false
             * -1首次登录 成功爬取数据 存入数据库 跳转添加必要信息
             * -2首次登录 爬取数据失败 密码错误 || 二次登录 密码错误
             * -3二次登录 密码正确
             */
            if(state==0){
                mv.addObject("msg","您不属于选题学生的范围或信息不完善");
                mv.setViewName("index");
            }else if(state==1){
                //创建session
                Student student= loginStudentService.findBySId(username);
                session.setAttribute("user",student);
                mv.setViewName("info_edit");
            }else if(state==2){
                mv.addObject("msg","密码错误");
                mv.setViewName("index");
            }else if(state==3){
                //创建session
                Student student= loginStudentService.findBySId(username);
                if(student.getsPhone()==null||student.getsEmail()==null){
                    mv.setViewName("info_edit");
                }else{
                    int collegeId=student.getMajor().getParentMId();
                    //查看该学院有多少个方向
                    List<Major>majorList= loginMajorService.findByCollegeId(collegeId);
                    JSONArray jsonArray=new JSONArray();
                    for (Major major:
                            majorList) {
                        //此方向有多少老师
                        List<Teacher>teacherList= loginTeacherService.findTeachersOfMajor(major);
                        for (Teacher teacher:
                                teacherList) {
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("tId",teacher.gettId());
                            jsonObject.put("tName",teacher.gettName());
                            jsonArray.add(jsonObject);
                        }
                    }
                    mv.addObject("teacherNav",jsonArray.toString());
                    mv.setViewName("student");
                }
                session.setAttribute("user",student);
            }
        }else if(role.equals("teacher")){
            String jsonStr= loginTeacherService.login(username,password);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            int state=jsonObject.getInt("state");
            /**
             * -0不属于选题老师的范围
             * -1登录成功+teacher对象
             * -2密码错误
             */
             if(state==0){
                 mv.addObject("msg","您不属于选题老师的范围");
                 mv.setViewName("index");
             }else if(state==1){
                 /**
                  * 复杂数据类型转换
                  */
                 Map classMap=new HashMap();
                 classMap.put("subjectSet",Subject.class);
                 JSONObject obj=jsonObject.getJSONObject("teacher");
                 Teacher teacher=(Teacher) JSONObject.toBean(obj,Teacher.class,classMap);
                 if(teacher.gettPhone().equals("")||teacher.equals("")){
                     mv.setViewName("info_edit");
                 }else{
                     JSONArray jsonArray=obj.getJSONArray("subjectSet");
                     JSONArray result=new JSONArray();
                     for (Object object:
                             jsonArray) {
                         JSONObject instance=(JSONObject)object;
                         String subName=instance.getString("subName");
                         String subId=instance.getString("subId");
                         int max=instance.getInt("subCount");
                         JSONObject temp=new JSONObject();
                         temp.put("subName",subName);temp.put("subId",subId);
                         temp.put("max",max);
                         result.add(temp);
                     }
                     session.setAttribute("subList",result.toString());
                     mv.addObject("subList",result.toString());
                     mv.setViewName("teacher");
                 }
                 session.setAttribute("user",teacher);
             }else if(state==2){
                 mv.addObject("msg","密码错误");
                 mv.setViewName("index");
             }
        }else if(role.equals("administrator")){
            /**
             * 管理员端待添加
             */
            if(!username.equals("root")){
                //设置管理员用户名
                mv.addObject("msg","管理员用户名错误");
                mv.setViewName("index");
            }else if(!password.equals("123456")){
                //设置管理员密码
                mv.addObject("msg","管理员密码错误");
                mv.setViewName("index");
            }else{
                //密码正确 跳转
                session.setAttribute("user","root");
                mv.setViewName("manner/manner");
            }
        }
        return mv;
    }
}
