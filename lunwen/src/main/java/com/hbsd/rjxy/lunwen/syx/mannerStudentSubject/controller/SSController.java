package com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.controller;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.service.SSService;
import com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.util.ExportExcel;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
public class SSController {
    @Resource
    private SSService SSService;

    /**
     * 实现按页查询的功能 <br>
     * <b>TODO 目前的BUG是：如果返回的值有空值的话，网页会报错。但是如果正常使用的话应该不会产生空值。</b>
     * @param model
     * @param page 要跳转到的页数。从<b>1</b>开始。
     * @return  "manner/manner_s_list" 网页地址
     * @author 孙亦璇*/
    @RequestMapping("/manner_s_list/{page}")
    public String slip(Model model, @PathVariable("page") int page){
        //0.设置
        int currentPage=page-1;  //设定一开始访问第一页
        if (currentPage<0) currentPage=0; //防止越界
        int pageSize=10; //设置每页展示的数量

        // 1.获取所要查询的内容主体
        Page<StudentWithSubject> list= SSService.findAll(currentPage,pageSize);
        model.addAttribute("s_slist",list);

        //2.获得记录数和页数
        int total=SSService.Count();  //获得总记录数
        model.addAttribute("total", total); //设置总记录数
        model.addAttribute("currentPage",currentPage+1); //设置当前页数
        model.addAttribute("NumOfPage",SSService.pageCount(pageSize,total));//设置总页数

        return "manner/manner_s_list";
    }

    /**
     * 访问学生选课的界面时调用的方法。采用get方法。（<b>没有写post</b>)
     * @param model
     * @return  "manner/manner_s_list" 网页地址
     * @author 孙亦璇
     */
    @GetMapping("/manner_s_list")
    public String manner( Model model )
    {
        slip(model,1);
        return "manner/manner_s_list";

    }

    /**
     * 用于删除记录的方法
     * @param model
     * @param ssId 由网页返回的要删除的对象在student&subject表中的id
     * @return "redirect:/manner_s_list" 重定向回manner_s_list
     * @author 孙亦璇
     */
    @RequestMapping("/manner_s_list/del/{ssId}")
    public  String del(Model model ,@PathVariable("ssId") int ssId){
        SSService.delSS(ssId);
        return "redirect:/manner_s_list";
    }

    /* ----------------------- 以下部分用于导出excel -----------------------*/

    /**
     * 一个用于返回时设置头文件的接口（为了接下来的代码复用所以摘出来写了）
     * 其中文件的返回名为 <b>当前时间戳.xls</b>
     * @param file 要返回的文件 （已存在服务器本地）
     * @return ResponseEntity
     * @author 孙亦璇
     */
    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls"); //设置文件名为 当前时间戳.xls
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

    //
    /**
     * 用于调用导出Excel的方法 {@link ExportExcel}，最终向网页返回文件
     * 生成的文件将存在项目运行的目录下
     * @param request 用于获取服务器运行的地址
     * @return ResponseEntity
     * @author 孙亦璇
     */
    @RequestMapping("/manner_s_list/output/exportExcel")
    public ResponseEntity<FileSystemResource> exportExcel(HttpServletRequest request){
        String realPath=request.getServletContext().getRealPath("/");
        System.out.println(realPath);
        List<StudentWithSubject> SSList=SSService.findAll();
        File file=new ExportExcel().exportExcel(SSList,realPath);
        return export(file);
    }
}
