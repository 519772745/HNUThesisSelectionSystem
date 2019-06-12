package com.hbsd.rjxy.lunwen.ljz.login.service;

import com.hbsd.rjxy.lunwen.entity.Major;
import com.hbsd.rjxy.lunwen.ljz.login.dao.LoginMajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginMajorService {
    @Autowired
    private LoginMajorRepository loginMajorRepository;

    /**
     * 查看该学院有多少个方向
     * @param parentId
     * @return
     */
    public List<Major>findByCollegeId(Integer parentId){
        Major major=new Major();
        major.setParentMId(parentId);
        Example<Major>example=Example.of(major);
        List<Major>majorList= loginMajorRepository.findAll(example);
        if(majorList!=null){
            return majorList;
        }
        return null;
    }
    public Major findByMName(String mName){
        Major major= loginMajorRepository.findByMName(mName);
        if (major!=null){
            return major;
        }
        return null;
    }
}
