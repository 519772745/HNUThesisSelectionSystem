package com.hbsd.rjxy.lunwen.nzr.mannerTeacher.controller;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.nzr.mannerTeacher.service.MannerteacherService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author：倪泽苒
 * 管理员端的教师管理页（manner_t.html）
 */
@Controller
public class MannerteacherCon {
    @Resource
    private MannerteacherService mannerteacherService;

    //外部访问的controller
@GetMapping("/manner_t")
//查询所有教师
public String manner( Model model )
    {    int pageNum=0;
        int size=10;

        //分页查询结果
        Page<Teacher> list= mannerteacherService.findAll(pageNum,size);
        //查询共多少页

        //页脚---共多少条记录
        int count=mannerteacherService.Count();

        model.addAttribute("tlist",list);
        model.addAttribute("count", count);
        model.addAttribute("page",pageNum);
        model.addAttribute("pageCount",mannerteacherService.pageCount(size,count));

        return "manner/manner_t";
}
//分页跳转
@GetMapping("/jump/{page}")
    public String mannerPage(Model model, @PathVariable("page") Integer pageNum){
    int size=10;
    Page<Teacher> list=mannerteacherService.findAll(pageNum,size);
    model.addAttribute("tlist",list);
    int count=mannerteacherService.Count();
    model.addAttribute("count", count);
    model.addAttribute("page",pageNum);
    model.addAttribute("pageCount",mannerteacherService.pageCount(size,count));
    return "manner/manner_t";
}
    //指定页码跳转的controller
@PostMapping("/manner_t")
public String  mannerjump(Model model ,HttpServletRequest request)
{
    int pageNum=Integer.parseInt(request.getParameter("page"))-1;
    int size=10;
    //Page<Teacher> list=mannerteacherService.findAll(pageNum,1);

    int count=mannerteacherService.Count();
    int pageCount=mannerteacherService.pageCount(size,count);
    if(pageNum+1>pageCount  || pageNum<0){
        Page<Teacher> list=mannerteacherService.findAll(0,size);
        model.addAttribute("tlist",list);
        pageNum=0;
    }
    else{
        Page<Teacher> list=mannerteacherService.findAll(pageNum,size);
        model.addAttribute("tlist",list);
    }
    model.addAttribute("count", count);
    model.addAttribute("page",pageNum);
    model.addAttribute("pageCount",pageCount);

    return "manner/manner_t";
}
//删除
@RequestMapping("/del/{tId}")
public  String del(Model model ,@PathVariable("tId") String tId){
mannerteacherService.delTeacher(tId);

    return "redirect:/manner_t";
}
//编辑保存
@RequestMapping("/edit.do/{index}/{oldid}")
    public String edit(@PathVariable("index") Integer index, @PathVariable("oldid") String id, HttpServletRequest request){
    //int index=ind-1;
    String tName=request.getParameter("tName"+index);
    String tPhone=request.getParameter("tPhone"+index);
    String tEmail=request.getParameter("tEmail"+index);
    String tId=request.getParameter("tId"+index);

    mannerteacherService.update(tId,tName,tPhone,tEmail,id);
   //Excel表导出的接口
    //mannerteacherService.toExcel();
    return "redirect:/manner_t";
}
@RequestMapping("/teacherExcel")
public void toExcel( HttpServletResponse response){
    mannerteacherService.toExcel(response,"教师信息");


}
}
