package com.hbsd.rjxy.lunwen.entity;

import org.hibernate.annotations.Persister;

import javax.persistence.*;

@Entity
@Table(name="tbl_major")
public class Major {
    /**
     * 标志id
     */
    private Integer mId;
    /**
     * 学院或专业方向的名称
     */
    private String mName;
    /**
     * 如果元组为学院：父id为0
     * 如果元组为专业：父id为所在学院的id
     */
    private Integer parentMId;
    @Id
    @Column(name="m_id")
    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }
    @Column(name="m_name")
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
    @Column(name="parent_id")
    public Integer getParentMId() {
        return parentMId;
    }

    public void setParentMId(Integer parentMId) {
        this.parentMId = parentMId;
    }
    @Transient
    @Override
    public String toString() {
        return "Major{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", parentMId=" + parentMId +
                '}';
    }
}
