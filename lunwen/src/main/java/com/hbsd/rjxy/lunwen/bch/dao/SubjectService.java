package com.hbsd.rjxy.lunwen.bch.dao;

import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

public interface SubjectService {

    /**
     * 分页: 直接按照id值分类分页
     * @param pageNum     页码
     * @param pageLimit   页面条数
     * @return
     */
    Page<Subject> getPage(Integer pageNum, Integer pageLimit, String tid);

    /**
     * 得到某个subject对象
     * @return
     */
    Subject get(Integer id);

    /**
     * 更新数据
     * @return
     */
    void update(Subject subject);

    /**
     * 学生选题后更改数据
     * @param subject
     */
    void selectToUpdate(Subject subject);

    /**
     * 根据 subject id删除数据
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据论文的题目查找论文
     * @param subName
     */
    boolean isExistSubName(String subName);

    /**
     * 持久化对象
     * @param subject
     */
    void saveSub(Subject subject);
}
