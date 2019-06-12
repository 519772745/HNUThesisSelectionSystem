package com.hbsd.rjxy.lunwen.entity;

import java.io.Serializable;

public class StuwithSub implements Serializable {
    private String stuId;
    private String subId;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    @Override
    public String toString() {
        return "StuwithSub{" +
                "stuId='" + stuId + '\'' +
                ", subId='" + subId + '\'' +
                '}';
    }
}
