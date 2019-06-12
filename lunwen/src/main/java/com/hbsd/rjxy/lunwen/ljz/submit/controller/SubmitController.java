package com.hbsd.rjxy.lunwen.ljz.submit.controller;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.ljz.submit.service.SubmitStudentService;
import com.hbsd.rjxy.lunwen.ljz.submit.service.SubmitSubjectService;
import com.hbsd.rjxy.lunwen.ljz.submit.service.SubmitSwsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class SubmitController {
    @Autowired
    private SubmitStudentService submitStudentService;
    @Autowired
    private SubmitSubjectService submitSubjectService;
    @Autowired
    private SubmitSwsService submitSwsService;
    @ResponseBody
    @PostMapping(value="/choose")
    public String chooseSubject(
            HttpSession httpSession,
            @RequestParam("arr")String arr){
        Student student=(Student) httpSession.getAttribute("user");
        /**
         * 重复提交清空之前的记录
         */


        List<StudentWithSubject>subjectList=submitSwsService.findSwsListByStudent(student);
        if(subjectList.size()>0){
            submitSwsService.deleteRecord(subjectList);
        }
        /*
        student.getStudentWithSubjectList().clear();
        submitStudentService.updateStudent(student);*/

        JSONArray jsonArray=JSONArray.fromObject(arr);
        for (Object object:
             jsonArray) {
            int subId=Integer.parseInt(object.toString());
            Subject subject=submitSubjectService.findSubjectBySubId(subId);
            Date date=new Date();
            Timestamp timestamp=new Timestamp(date.getTime());
            StudentWithSubject sws=new StudentWithSubject();
            sws.setState(0);
            //选题的学生状态
            student.setSubState(1);
            submitStudentService.updateStudent(student);
            sws.setStudent(student);
            sws.setSubject(subject);
            sws.setTimestamp(timestamp);
            submitSwsService.saveRecord(sws);
        }
        return null;
    }
    @ResponseBody
    @PostMapping(value = "/check")
    public String checkRecord(
            @RequestParam(value = "sId")String sId){
        Student student=submitStudentService.findStudentBySId(sId);
        List<StudentWithSubject> swsList=student.getStudentWithSubjectList();
        JSONArray jsonArray=new JSONArray();
        for (StudentWithSubject sws:
                swsList) {
            JSONObject jsonObject=new JSONObject();
            int state;
            if(sws.getState()==1){
                //师生双方确立关系
                state=1;
            }else{
                state=0;
            }
            jsonObject.put("subName",sws.getSubject().getSubName());
            jsonObject.put("subId",sws.getSubject().getSubId());
            jsonObject.put("tName",sws.getSubject().getTeacher().gettName());
            jsonObject.put("state",state);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}
