package com.hbsd.rjxy.lunwen.xcl.manner.controller;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.xcl.manner.service.MannerServiceImpl;
import com.hbsd.rjxy.lunwen.xcl.manner.service.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
@Controller
public class MannerController {
    @Autowired
    MannerServiceImpl mannerServiceImpl;
    @Autowired
    TeacherServiceImpl teacherServiceImpl;
    /*
    所有选题数据 分页
     */
    @GetMapping("manner_list")
    public String pageSub(Model model,HttpServletRequest request,Integer page,Integer size){
        size=10;
        HttpSession session=request.getSession();
        List<Teacher> teacherList=teacherServiceImpl.findAll();
        Page<Subject> pages= mannerServiceImpl.pageSub(page, size);
        model.addAttribute("sublist",pages);
        session.setAttribute("tlist",teacherList);
        session.setAttribute("teacher","all");
        session.setAttribute("state",-1);
        return "manner/manner_list";
    }
    //条件查询 页面跳转
    @PostMapping("manner_list")
    public  String pageSub2(Model model, HttpServletRequest request, HttpServletResponse response, Integer size){
        size=10;//每页展示的条数
        Integer jumpNum = 0;//指定页面跳转的页数
        HttpSession session=request.getSession();
        String sta=request.getParameter("state");
        String teacher=request.getParameter("teacher");//老师名字 默认all
        Integer state=Integer.parseInt(request.getParameter("state"));//状态 默认-1 已完成 1 未完成 0
        Integer t=Integer.parseInt(request.getParameter("aaaaa")) ;  //代表 t=0首页 t=1/2上/下一页 t=3尾页 t=4跳转
        Integer page=Integer.parseInt(request.getParameter("page")) ;//获取当前页数
        Integer tp=Integer.parseInt(request.getParameter("tp")) ;//获取总页数
        String jumpNumString  = request.getParameter("jumpNum");//指定的页面跳转页数  默认String 判断不为空后转为Integer
        if (teacher.equals("all")){  //如果teacher为 默认的all  查询时teacher=null
            teacher=null;
        }
        // 判断跳转的页数是否正确
        if("".equals(jumpNumString)||jumpNumString == null){
            jumpNum = 0;
        }else{
            jumpNum=Integer.parseInt(jumpNumString);
        }
        //区分页面跳转按钮
        if (t==0){
            page=0;
        }else if (t==1){
            page=page-1;
        }else if (t==2){
            page=page+1;
        }else if (t==3){
            page=tp-1;
        }else if(t==4){
            page=jumpNum-1;
        }else if (t==5){
            //输入的页码错误 返回当前页
        }
        Page<Subject> pages= mannerServiceImpl.pageSub2(teacher,state,page,size);
        model.addAttribute("sublist",pages);
        session.setAttribute("teacher",teacher);
        session.setAttribute("state",state);
        return "manner/manner_list";
    }
    //导出数据到Excel
    @PostMapping("exportExcel")
    public void exportEx(HttpServletRequest request,HttpServletResponse response){
        String fName="Subject";//定义初始文件名
        String teacher=request.getParameter("teacher");//老师名字 默认all
        if (teacher.equals("all")){  //如果teacher为 默认的all  查询时teacher=null
            teacher=null;
        }
        Integer state=Integer.parseInt(request.getParameter("state"));//状态 默认-1 已完成 1 未完成 0
        //输出的Excel表头 可选项："选题编号","论文名称","指导教师","上线量","剩余量","是否自选"
        List<String> rowName= Arrays.asList("选题编号","论文名称","指导教师","上线量","剩余量","是否自选");
        List<Subject> userList= mannerServiceImpl.exportE(teacher,state);
        SubjectToExcel s=new SubjectToExcel();
        s.createExcelFile(response,userList,rowName,fName);
        return ;
    }
    //编辑、更新选题
    @PutMapping("/edit/{id}")
    public String editSub(HttpServletRequest request,@PathVariable("id") Integer id){
        Subject subject=new Subject();
        Teacher teacher=new Teacher();
        String  subName=request.getParameter("subName"+id);//论题名称
        Integer subId=Integer.parseInt(request.getParameter("subId"+id));//选题编号
        String tName=request.getParameter("tName"+id);//指导教师
        Integer subCount=Integer.parseInt(request.getParameter("subCount"+id));//上线量
        Integer selectedNum=Integer.parseInt(request.getParameter("selectedNum"+id));//已选人数=上线量-剩余量
        subject= mannerServiceImpl.findSubjectById(id);//由id获取 当前编辑的Subject
        teacher=subject.getTeacher();
        teacher.settName(tName);
        subject.setSubId(subId);
        subject.setSubName(subName);
        subject.setTeacher(teacher);
        subject.setSubCount(subCount);
        subject.setRemain(subCount-selectedNum);//剩余量=上线量-已选人数
        mannerServiceImpl.updateSub(subject);//更新数据
        return "redirect:/manner_list";//返回
    }

}
