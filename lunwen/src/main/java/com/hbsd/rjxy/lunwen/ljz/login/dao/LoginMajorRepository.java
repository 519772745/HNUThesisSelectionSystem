package com.hbsd.rjxy.lunwen.ljz.login.dao;

import com.hbsd.rjxy.lunwen.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMajorRepository extends JpaRepository<Major,Integer> {
    public Major findByMName(String mName);
}
