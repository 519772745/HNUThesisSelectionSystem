package com.hbsd.rjxy.lunwen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_subject")
public class Subject {
    /**
     * 选题号 自增
     */
    private Integer subId;
    /**
     * 选题名称
     */
    private String subName;
    /**
     * 介绍文档在服务器上存储的路径
     */
    private String docPath;
    /**
     * 上线量
     */
    private Integer subCount;
    /**
     * 剩余量
     */
    private Integer remain;
    /**
     * 是否自选
     *  -0不是自选命题
     *  -1是自选命题
     */
    private Integer isSelf;
    /**
     * 属于那个教师
     */
    private Teacher teacher;
    private List<StudentWithSubject>studentWithSubjectList=new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            mappedBy = "subject")
    public List<StudentWithSubject> getStudentWithSubjectList() {
        return studentWithSubjectList;
    }

    public void setStudentWithSubjectList(List<StudentWithSubject> studentWithSubjectList) {
        this.studentWithSubjectList = studentWithSubjectList;
    }

    @Id
    @Column(name="sub_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }
    @Column(name="sub_name")
    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
    @Column(name="path")
    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }
    @Column(name="sub_count")
    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }
    @Column(name="remain")
    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }
    @Column(name="isfree")
    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }
    @JoinColumn(name="t_id")
    @ManyToOne(fetch = FetchType.EAGER)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getParamValue(String s) {
        if (s=="指导教师"){
            return teacher.gettName();
        }else if (s=="选题编号"){
            return subId.toString();
        }else if (s=="论文名称"){
            return subName;
        }else if (s=="上线量"){
            return subCount.toString();
        }else if (s=="剩余量"){
            return remain.toString();
        }else if (s=="是否自选"){
            return isSelf==1?"是":"否";
        }else {
            return "NaN";
        }

    }
}
