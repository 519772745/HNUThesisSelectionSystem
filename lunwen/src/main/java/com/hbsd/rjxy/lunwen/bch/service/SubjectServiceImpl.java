package com.hbsd.rjxy.lunwen.bch.service;

import com.hbsd.rjxy.lunwen.bch.dao.SubjectRepository;
import com.hbsd.rjxy.lunwen.bch.dao.SubjectService;
import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // 分页
    @Override
    @Transactional(readOnly = true)
    public Page<Subject> getPage(Integer pageNum, Integer pageLimit, String tid) {
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "sub_id");
        Pageable pageable = PageRequest.of(pageNum, pageLimit, sort);
        return subjectRepository.selectAllByTId(tid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Subject get(Integer id) {
        Subject subject = subjectRepository.findById(id).get();
        return subject;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Subject subject) {
        subjectRepository.update(subject);
    }

    @Override
    public void selectToUpdate(Subject subject) {
        subjectRepository.selectToUpdate(subject);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Integer id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public boolean isExistSubName(String subName) {
        List<Subject> list = subjectRepository.findBySubName(subName);
        System.out.println(list.size());
        if (list.size() != 0){
            return true;
        }else return false;
    }

    @Override
    public void saveSub(Subject subject) {
        subjectRepository.save(subject);
    }
}
