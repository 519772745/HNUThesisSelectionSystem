package com.hbsd.rjxy.lunwen.lcl.dao;

import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LclSwsRepository extends JpaRepository<StudentWithSubject,Integer> {

    @Modifying
    @Query("delete from StudentWithSubject sws where sws.id=?1")
    public int deleteSwsById(Integer id);
}
