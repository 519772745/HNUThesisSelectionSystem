package com.hbsd.rjxy.lunwen.bch.service;

import com.hbsd.rjxy.lunwen.bch.dao.ApplyRepository;
import com.hbsd.rjxy.lunwen.bch.dao.ApplyService;
import com.hbsd.rjxy.lunwen.bch.dao.SubjectRepository;
import com.hbsd.rjxy.lunwen.entity.Student;
import com.hbsd.rjxy.lunwen.entity.StudentWithSubject;
import com.hbsd.rjxy.lunwen.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * 根据 subId 查找论文，然后得到学生列表
     * @param subId
     * @return
     */
    @Override
    public Page<Student> getStuWithSub(Integer subId,Integer pageNum, Integer pageLimit) {
        Subject subject = subjectRepository.findById(subId).get();
        List<StudentWithSubject>  studentWithSubjects= subject.getStudentWithSubjectList();
        List<Student> students = new ArrayList<Student>();
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "sId");
        Pageable pageable = PageRequest.of(pageNum, pageLimit, sort);
        for (StudentWithSubject s : studentWithSubjects){
            if (s.getStudent().getSubState() == 0 || s.getStudent().getSubState() == 1)  //选取没有选题成功的学生对象
                students.add(s.getStudent());
        }
        // 删除重复元素
        HashSet<Student> set = new HashSet<Student>(students);
        students.clear();
        students.addAll(set);
        // 当前页第一条数据在List中的位置
        int start = (int)pageable.getOffset();
        // 当前页最后一条数据在List中的位置
        int end = (start + pageable.getPageSize()) > students.size() ? students.size() : ( start + pageable.getPageSize());
        Page<Student> page = new PageImpl<Student>(students.subList(start, end),pageable, students.size());
        return page;
    }

    /**
     * 更改学生的状态成为 2  表示学生选定选题
     * @param student
     */
    @Override
    public void updateStu(Student student) {
        applyRepository.updateSub(student);
    }

    @Override
    public Student get(String id) {
        Student student = applyRepository.findById(id).get(0);
        return student;
    }
}
