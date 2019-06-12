package com.hbsd.rjxy.lunwen.xcl.manner.dao;

import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MannerRePository extends JpaRepository<Subject,Integer>, JpaSpecificationExecutor<Subject> {
    Page<Subject> findAll(Specification<Subject> spec, Pageable pageable);
}
