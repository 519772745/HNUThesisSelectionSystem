package com.hbsd.rjxy.lunwen.nzr.mannerTeacher.service;

import com.hbsd.rjxy.lunwen.entity.Teacher;
import com.hbsd.rjxy.lunwen.nzr.mannerTeacher.dao.MannerteacherDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MannerteacherService {
    @Resource
  private MannerteacherDao mannerteacherDao;
//以教工号顺序,分页查询所有教师
  public Page<Teacher> findAll(int pageNumber, int pageSize){
      Sort sort=new Sort(Sort.Direction.ASC,"tId");

        if(pageNumber==0) {
            Pageable pageable =new PageRequest(0,pageSize,sort);
            return mannerteacherDao.findAll(pageable);
        }
        else{
            Pageable pageable =new PageRequest(pageNumber,pageSize,sort);
            return mannerteacherDao.findAll(pageable);
        }

  }
//教师总人数    共多少条记录
    public int Count(){
      return (int)mannerteacherDao.count();
    }
//查询总页数
public int pageCount(int pageSize,int count){
      int a=count % pageSize;
      if (a==0)
          return count/pageSize;
      else
          return count/pageSize+1;

}
//删除指定教师
    public void delTeacher(String tId){
      mannerteacherDao.deleteById(tId);
    }
//编辑后保存教师对象
@Transactional
    public void update(String id,String name,String phone,String email,String old){
      Teacher teacher=mannerteacherDao.findById(old).get();
    System.out.println(teacher+"   baocun   "+old);

      teacher.settId(id);
      teacher.settEmail(email);
      teacher.settName(name);
      teacher.settPhone(phone);
      mannerteacherDao.save(teacher);

    }
  public void toExcel(HttpServletResponse response, String fName){
      Sort sort=new Sort(Sort.Direction.ASC,"tId");
      List<Teacher> list= mannerteacherDao.findAll(sort);
      teacherExcel t=new teacherExcel();
      List<String> rowname=new ArrayList<String>();

      rowname.add("教职工号");
      rowname.add("姓名");
      //rowname.add("学院");
      rowname.add("邮箱");
      rowname.add("电话");
      t.createExcelFile(response,list,rowname,fName);
  }
}
