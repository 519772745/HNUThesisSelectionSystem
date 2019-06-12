package com.hbsd.rjxy.lunwen.bch.controller;

import com.hbsd.rjxy.lunwen.bch.dao.SubjectService;
import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.entity.Teacher;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 分页控制
     *
     * @param start
     * @param limit
     * @param model
     * @return
     */
    @GetMapping("/subjects")
    public String getList(@RequestParam(value = "start", defaultValue = "0") Integer start,
                          @RequestParam(value = "limit", defaultValue = "6") Integer limit,
                          HttpSession session,
                          Model model) {
        Teacher teacher=(Teacher)session.getAttribute("user");
        start = start < 0 ? 1 : start;
        Page<Subject> page = subjectService.getPage(start, limit,teacher.gettId());
        model.addAttribute("page", page);
        return "topiclist";
    }

    /**
     * 进入修改页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/subject/{id}")
    public String toUpdate(@PathVariable("id") Integer id,
                           Model model) {
        Subject subject = subjectService.get(id);
        model.addAttribute("subject", subject);
        System.out.println("未修改的" + subject);
        return "topicedit";
    }


    /**
     * 修改信息
     * @param fyDhCode
     * @param fyCh
     * @param id
     * @param file
     * @param model
     * @param request
     * @return
     */
    @PutMapping("/subject")
    public String updateSub(@RequestParam String fyDhCode,
                            @RequestParam Integer fyCh,
                            @RequestParam Integer id,
                            MultipartFile file,
                            Model model,
                            HttpServletRequest request) {
        if (file.getSize() > (1024 * 1024 * 5)) {
            System.out.println("文件上传失败");
            model.addAttribute("msg", "文件过大，请上传5M以内的图片");
            return "/topicedit";
        }
        String path = request.getContextPath();
        String basePath = request.getScheme() +"://"+ request.getServerName() + ":" +
                request.getServerPort() + path;
        Date dt = new Date();
        Long time = dt.getTime();
        if (file != null) {
            String realPath = "d://uploadFile/";// 获取保存的路径，本地磁盘中的一个文件夹
            //文件原名称
            String originFileName = "";
            // 上传文件重命名
            String originalFilename = time.toString().substring(time.toString().length() - 8, time.toString().length());
            originalFilename = originalFilename.concat(".");
            originalFilename = originalFilename.concat(file.getOriginalFilename().toString()
                    .substring(file.getOriginalFilename().toString().indexOf(".") + 1));
            // 这里使用Apache的FileUtils方法来进行保存
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
                Subject subject = subjectService.get(id);
                subject.setSubName(fyDhCode);
                subject.setSubCount(fyCh);
                subject.setDocPath(realPath + originalFilename);
                subject.setRemain(fyCh);
                subjectService.update(subject);
                System.out.println("修改之后的" + subject);
                System.out.println("上传成功,保存成功");
                model.addAttribute("msg", "上传成功,保存成功");
                model.addAttribute("subject", subject);   //辅助变量
            } catch (IOException e) {
                System.out.println("文件上传失败");
                model.addAttribute("msg", "文件上传失败");
                e.printStackTrace();
            }
        }
        return "topicedit";
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("/subject/{id}")
    public String deleteSub(@PathVariable("id") Integer id) {
        subjectService.deleteById(id);
        System.out.println("成功删除" + id);
        return "redirect:/subjects";
    }

    /**
     * ajax检查是否重复
     * @param subName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkSubName")
    public Map<String, String> out(@RequestParam("subName") String subName) {
        //map集合用来存放返回值
        Map<String, String> map = new HashMap<String, String>();
        if (subjectService.isExistSubName(subName)) {
            map.put("result", "查到了");
        } else
            map.put("result", "未查到");
        return map;
    }

    /**
     * 上传文件并保存
     * @param file
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/upload")
    public String uploadApk(MultipartFile file, HttpServletRequest request,HttpSession httpSession,
                                         Model model) {
        String subName = request.getParameter("fyDhCode");
        String subCount = request.getParameter("fyCh");
        Integer count = Integer.parseInt(subCount);
        if (file.getSize() > (1024 * 1024 * 5)) {
            System.out.println("文件上传失败");
            model.addAttribute("msg", "文件过大，请上传5M以内的图片");
            return "topicedit";
        }
        String path = request.getContextPath();
        String basePath = request.getScheme() +"://"+ request.getServerName() + ":" +
                request.getServerPort() + path;
        Date dt = new Date();
        Long time = dt.getTime();
        if (file != null) {
            String realPath = "d://uploadFile/";// 获取保存的路径，本地磁盘中的一个文件夹
            //文件原名称
            String originFileName = "";
            // 上传文件重命名
            String originalFilename = time.toString().substring(time.toString().length() - 8, time.toString().length());
            originalFilename = originalFilename.concat(".");
            originalFilename = originalFilename.concat(file.getOriginalFilename().toString()
                    .substring(file.getOriginalFilename().toString().indexOf(".") + 1));
            // 这里使用Apache的FileUtils方法来进行保存
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originalFilename));
                Subject subject = new Subject();
                subject.setSubName(subName);
                subject.setSubCount(count);
                subject.setDocPath(realPath + originalFilename);
                subject.setIsSelf(0);
                subject.setRemain(count);
                Teacher teacher=(Teacher) httpSession.getAttribute("user");
                subject.setTeacher(teacher);
                subjectService.saveSub(subject);
                System.out.println("上传成功,保存成功");
                model.addAttribute("msg", "上传成功,保存成功");
            } catch (IOException e) {
                System.out.println("文件上传失败");
                model.addAttribute("msg", "文件上传失败");
                e.printStackTrace();
            }
        }
        return "topicedit";
    }

}
