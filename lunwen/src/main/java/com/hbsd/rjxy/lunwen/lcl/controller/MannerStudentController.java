package com.hbsd.rjxy.lunwen.lcl.controller;

import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.lcl.service.StudentServiceImpl;
import com.hbsd.rjxy.lunwen.lcl.util.PageBean;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class MannerStudentController {
    @Autowired
    private StudentServiceImpl studentService;

//    //查询所有学生信息list形式保存载Attribute中
//    @GetMapping("manner_s")
//    public String showAll(Model model, HttpServletRequest request, HttpServletResponse response){
//        //获取所有学生
//        List<Student> stuList = studentService.findAll();
//        //学生总数
//        int totalCount = studentService.totalCount();
//        //存进Attribute中
//        model.addAttribute("stuList",stuList);
//        model.addAttribute("totalCount",totalCount);
//        return "manner/manner_s";
//    }

    //删除学生
    @GetMapping("/delete")
    public String delete(Model model, HttpServletRequest request, HttpServletResponse response){
        String sid = request.getParameter("sid");
        System.out.println(sid);
        studentService.delete(sid);
        return "redirect:/manner_s";
    }

    //分页展示学生基本信息
    @GetMapping("/manner_s")
    public String showWithPages(Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 10;
        int pageNum = 1;
        int totalCount = studentService.findAll().size();
        String topage = request.getParameter("topage");
        String pn = request.getParameter("pageNum");
        if (pn != null){
            pageNum = parseInt(pn);
        }
        if (topage != null){
            if (parseInt(topage) >0 && parseInt(topage) <= (totalCount/pageSize)+1){
                pageNum = parseInt(topage);
            }else {pageNum = 1;}
        }
        if (pageNum <= 0){
            pageNum = 1;
        }
        if (pageNum >= (totalCount/pageSize)+1){
            pageNum = (totalCount/pageSize)+1;
        }

        PageBean<Student> pageBean =  studentService.findPageBean(pageNum,pageSize);
        List<Student> stuList = pageBean.getList();


        model.addAttribute("stuList",stuList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPage",pageBean.getTotalPage());

        return "manner/manner_s";
    }

    //导出excel
    @GetMapping("/toExcel")
    public void toExcel(HttpServletResponse response)throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生信息表");
        List<Student> list = studentService.findAll();
        String fileName = "StudentInfo"  + ".xls";//设置要导出的文件的名字
        int rowNum = 1;
        //headers表示excel表中第一行的表头
        String[] headers = { "学号", "姓名", "手机号", "邮箱"};
        //在excel表中添加表头
        HSSFRow row = sheet.createRow(0);
        //创建表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //填写信息
        for (Student stu:list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(stu.getsId());
            row1.createCell(1).setCellValue(stu.getsName());
            row1.createCell(2).setCellValue(stu.getsPhone());
            row1.createCell(3).setCellValue(stu.getsEmail());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }
    }



