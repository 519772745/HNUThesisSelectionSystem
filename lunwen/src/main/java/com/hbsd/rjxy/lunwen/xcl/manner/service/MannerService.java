package com.hbsd.rjxy.lunwen.xcl.manner.service;

import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MannerService {
    public Subject findSubjectById(Integer id);
    public void updateSub(Subject subject);
    public List<Subject> exportE(String t, Integer s);
    public Page<Subject> pageSub(Integer page, Integer size);
    public Page<Subject> pageSub2(String t, Integer s, Integer page, Integer size);
}
