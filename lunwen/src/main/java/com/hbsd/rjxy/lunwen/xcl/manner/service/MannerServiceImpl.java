package com.hbsd.rjxy.lunwen.xcl.manner.service;

import com.hbsd.rjxy.lunwen.entity.Subject;
import com.hbsd.rjxy.lunwen.xcl.manner.dao.MannerRePository;
import com.hbsd.rjxy.lunwen.xcl.manner.dao.TeacherRePository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class MannerServiceImpl implements MannerService {
    @Autowired
    private MannerRePository mannerRePository;
    private TeacherRePository teacherRePository;
    public Subject findSubjectById(Integer id){
        Subject subject= mannerRePository.getOne(id);
        return subject;
    }
    public void updateSub(Subject subject) {
        mannerRePository.saveAndFlush(subject);
        return ;
    }
    public Page<Subject> pageSub(Integer page, Integer size) {
        if (null == page) {
            page = 0;
        }
        if (size == 0 || size == null) {
            size = 5;
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.ASC, "subId");
        Page<Subject> pages = mannerRePository.findAll(pageable);
        return pages;
    }
    //条件分页查询的数据 Page 每次只有一Page数据
    public Page<Subject> pageSub2(String t, Integer s, Integer page, Integer size){
        Page<Subject> resultPage=null;

        Specification<Subject> querySpec=new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates =new ArrayList<>();
                if (null!=t){
                    predicates.add(criteriaBuilder.equal(root.get("teacher").get("tName"),t));
                }
                if (null!=s&&s>=0){
                    if (s.equals(0)){  //s==0 未完成  count>0
                        predicates.add(criteriaBuilder.greaterThan(root.get("remain"),s));
                    }else{ //s==1 已完成 count<1
                        predicates.add(criteriaBuilder.lessThan(root.get("remain"),s));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.ASC, "subId");
        resultPage=this.mannerRePository.findAll(querySpec,pageable);
        return resultPage;
    }
    //导出到Excel的数据List 所有的
    public List<Subject> exportE(String t, Integer s){
        List<Subject> list=null;
        Specification<Subject> querySpec=new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates =new ArrayList<>();
                if (null!=t){
                    predicates.add(criteriaBuilder.equal(root.get("teacher").get("tName"),t));
                }
                if (null!=s&&s>=0){
                    if (s.equals(0)){  //s==0 未完成  count>0
                        predicates.add(criteriaBuilder.greaterThan(root.get("remain"),s));
                    }else{ //s==1 已完成 count<1
                        predicates.add(criteriaBuilder.lessThan(root.get("remain"),s));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        list=this.mannerRePository.findAll(querySpec);
        return list;
    }
}

