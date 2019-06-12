package com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.service;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.syx.mannerStudentSubject.dao.SSDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SSService {
    @Resource
    private SSDao SSDao;
    /**
     * 查找所有的结果（<b>用于生成Excel</b>）<br>
     * <b>所有输出默认按学号顺序排序</b>
     * @return List<StudentWithSubject> student&subject列表
     * @author 孙亦璇
     */
    public List<StudentWithSubject> findAll(){
        Sort sort=new Sort(Sort.Direction.ASC,"student");
        return SSDao.findAll();

    }
    /**
     * 分页查询数据库（<b>用于网页访问</b>）<br>
     * <b>所有输出默认按学号顺序排序</b>
     * @param pageNumber 要访问的页数（从0开始）
     * @param pageSize 一页显示的数量
     * @return Page<StudentWithSubject>
     * @author 孙亦璇
     */
    public Page<StudentWithSubject> findAll(int pageNumber, int pageSize){
        Sort sort=new Sort(Sort.Direction.ASC,"id");

            Pageable pageable =new PageRequest(pageNumber,pageSize,sort);
            return SSDao.findAll(pageable);

    }

    /**
     * 返回记录总数（用于统计总记录数）
     * @return int
     * @author 孙亦璇
     */
    public int Count(){
        return (int)SSDao.count();
    }

    /**
     * 计算页面页数
     * @param pageSize 页面大小
     * @param count 总记录数
     * @return int 页数
     * @author 孙亦璇
     */
    public int pageCount(int pageSize,int count){
        if (count % pageSize==0)
            return count/pageSize;
        else
            return count/pageSize+1;

    }
    /**
     * 删除记录
     * @param ssId 由网页返回的要删除的对象在student&subject表中的id
     * @author 孙亦璇
     */
    public void  delSS(int ssId){
        SSDao.deleteSSById(ssId);
    }
}
