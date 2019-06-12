package com.hbsd.rjxy.lunwen.ljz.navigator.controller;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.ljz.navigator.service.NavigatorTeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class StuNavHandler {
    @Autowired
    NavigatorTeacherService navigatorTeacherService;
    @GetMapping("/fs")
    @ResponseBody
    public ModelAndView findHisSubject(
            @RequestParam("tId")String tId){
        Teacher teacher= navigatorTeacherService.findTeacherByTId(tId);
        Set<Subject> subjectSet=teacher.getSubjectSet();

        /*
        for (Subject subject:
                subjectSet ) {
            System.out.println(subject.getSubId()+":"+subject.getSubName());
        }*/
        /*
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(
                CycleDetectionStrategy.LENIENT
        );
        JSONArray jsonArray=JSONArray.fromObject(subjectList,jsonConfig);*/
        ModelAndView mv=new ModelAndView();
        mv.addObject(subjectSet);
        mv.setViewName("question_list");
        return mv;
    }
}
