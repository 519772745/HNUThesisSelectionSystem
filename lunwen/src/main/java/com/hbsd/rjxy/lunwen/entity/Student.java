package com.hbsd.rjxy.lunwen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_student")
public class Student {
    /**
     * 学号
     */
    private String sId;
    /**
     * 教务密码
     */
    private String sPwd;
    /**
     * 姓名（爬虫）
     */
    private String sName;
    /**
     * 手机号（学生手动添加）
     */
    private String sPhone;
    /**
     * 邮箱（学生手动添加）
     */
    private String sEmail;
    /**
     * 年级（爬虫）
     */
    private String grade;
    /**
     * 专业方向及学院（爬虫）
     */
    private Major major;
    /**
     * 毕业绩点（爬虫）
     */
    private Double oblJd;
    /**
     * 选修加权分数（爬虫）
     */
    private Double eleScore;
    /**
     * 第二课堂（爬虫）
     */
    private Double scScore;
    /**
     * 检查是否第一次登录：
     *  如果第一次登录，需要模拟登录爬取在校信息
     *  如果不是第一次登录，直接访问数据库
     *      -0 没有登录过
     *      -1 登陆过
     */
    private Integer times;
    /**
     *选课状态
     *  -0 没有选课
     *  -1 已经选题但是老师没有接收
     *  -2 已经选题且老师接收
     */
    private Integer subState;

    private List<StudentWithSubject>studentWithSubjectList=new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,
       // cascade = {CascadeType.REMOVE},
        mappedBy = "student")
    public List<StudentWithSubject> getStudentWithSubjectList() {
        return studentWithSubjectList;
    }

    public void setStudentWithSubjectList(List<StudentWithSubject> studentWithSubjectList) {
        this.studentWithSubjectList = studentWithSubjectList;
    }

    @Id
    @Column(name="s_id")
    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
    @Column(name="s_password")
    public String getsPwd() {
        return sPwd;
    }

    public void setsPwd(String sPwd) {
        this.sPwd = sPwd;
    }
    @Column(name="s_name")
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
    @Column(name="s_phone")
    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
    @Column(name="s_email")
    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }
    @Column(name="grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    @JoinColumn(name="m_id")
    @ManyToOne(fetch = FetchType.EAGER)
    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
    @Column(name="required")
    public Double getOblJd() {
        return oblJd;
    }

    public void setOblJd(Double oblJd) {
        this.oblJd = oblJd;
    }
    @Column(name="optional")
    public Double getEleScore() {
        return eleScore;
    }

    public void setEleScore(Double eleScore) {
        this.eleScore = eleScore;
    }
    @Column(name="sec_class")
    public Double getScScore() {
        return scScore;
    }

    public void setScScore(Double scScore) {
        this.scScore = scScore;
    }
    @Column(name="times")
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
    @Column(name="subject_state")
    public Integer getSubState() {
        return subState;
    }

    public void setSubState(Integer subState) {
        this.subState = subState;
    }
}
