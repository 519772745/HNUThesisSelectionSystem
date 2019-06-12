package com.hbsd.rjxy.lunwen.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tbl_teacher")
public class Teacher {
    /**
     * 教职工号
     */
    private String tId;
    /**
     * 教职工密码
     */
    private String tPwd;
    /**
     * 教职工姓名
     */
    private String tName;
    /**
     * 属于那个方向及学院
     */
    private Major major;
    /**
     * 手机号
     */
    private String tPhone;
    /**
     * 邮件
     */
    private String tEmail;
    /**
     * 所带的命题集合
     */
    private Set<Subject> subjectSet=new HashSet<>();
    @Id
    @Column(name="t_id")
    public String gettId() {
        return tId;
    }

    /**
     * 若在1的一端的@OneToMany 中使用mapperBy属性 则@OneToMany 端就不能再使用@JoinColunm属性 命名冲突
     * @return
     */
    @OneToMany(fetch = FetchType.EAGER,
        cascade = {CascadeType.MERGE},
        mappedBy = "teacher")
    public Set<Subject> getSubjectSet() {
        return subjectSet;
    }

    public void setSubjectSet(Set<Subject> subjectSet) {
        this.subjectSet = subjectSet;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
    @Column(name="t_password")
    public String gettPwd() {
        return tPwd;
    }

    public void settPwd(String tPwd) {
        this.tPwd = tPwd;
    }
    @Column(name="t_name")
    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
    @JoinColumn(name="m_id")
    @ManyToOne(fetch = FetchType.EAGER)
    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
    @Column(name="t_phone")
    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }
    @Column(name="t_email")
    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getParamValue(String name){
        if (name=="教职工号")
            return this.tId;
        else if (name=="姓名")
            return  this.tName;
        else if (name=="电话")
            return this.tPhone;
        else if (name=="邮箱")
            return this.tEmail;
        else if (name=="学院")
            return this.major.getmName();
        return "";
    }
}
