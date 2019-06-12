package com.hbsd.rjxy.lunwen.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_student_subject")
public class StudentWithSubject {
    private Integer id;
    private Student student;
    private Subject subject;
    private Timestamp timestamp;
    /**
     * -0 双方未选
     * -1 双方达成互选
     */
    private Integer state;
    @Id
    @Column(name="a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    @Column(name="timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    @Column(name="a_state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
