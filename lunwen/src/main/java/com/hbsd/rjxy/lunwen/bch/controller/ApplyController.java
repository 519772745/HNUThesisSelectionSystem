package com.hbsd.rjxy.lunwen.bch.controller;

import com.hbsd.rjxy.lunwen.bch.dao.ApplyService;
import com.hbsd.rjxy.lunwen.bch.dao.StuWithSubService;
import com.hbsd.rjxy.lunwen.bch.dao.SubjectService;
import com.hbsd.rjxy.lunwen.entity.*;
import com.hbsd.rjxy.lunwen.util.date.StringToDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StuWithSubService stuWithSubService;

    /**
     * 按条件的查找并分页
     * @param subId
     * @param start
     * @param limit
     * @param model
     * @return
     */
    @RequestMapping("/students")
    public String getStudent(@RequestParam(value = "subId",defaultValue = "20") Integer subId,
                              @RequestParam(value = "start", defaultValue = "0") Integer start,
                              @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                              Model model){
        start = start < 0 ? 1 : start;
        Page<Student> list = applyService.getStuWithSub(subId,start,limit);
        model.addAttribute("page", list);
        Subject subject = subjectService.get(subId);
        model.addAttribute("subject", subject);
        return "/applylist";
    }

    @ResponseBody
    @RequestMapping(value = "/selectTopic", method = RequestMethod.POST)
    public Map<String, String> selecteTopic(@RequestBody StuwithSubList stuwithSubList, HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
        Timestamp time = StringToDate.getDate();
        if (stuwithSubList.getStuwithLst().size() == 0){
            map.put("result", "no");
            return map;
        }else {
            for (StuwithSub stuwithSub : stuwithSubList.getStuwithLst()) {
                Subject subject = subjectService.get(Integer.parseInt(stuwithSub.getSubId()));
                Integer count = subject.getRemain();
                Student student = applyService.get(stuwithSub.getStuId());
                StudentWithSubject studentWithSubject = new StudentWithSubject();
                studentWithSubject.setState(1);
                studentWithSubject.setStudent(student);
                studentWithSubject.setSubject(subject);
                studentWithSubject.setTimestamp(time);
                stuWithSubService.savaStuWithSub(studentWithSubject);
                System.out.println("更新stuwithsub成功");

                student.setSubState(2);
                applyService.updateStu(student);
                System.out.println("学生表更新成功");

                subject.setRemain(count - 1);
                subjectService.selectToUpdate(subject);
                System.out.println("选题表更新成功");

                System.out.println(stuwithSub);
            }

            map.put("result", "ok");
            return map;
        }
    }
}
