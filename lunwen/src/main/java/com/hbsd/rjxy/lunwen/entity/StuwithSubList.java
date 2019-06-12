package com.hbsd.rjxy.lunwen.entity;

import java.io.Serializable;
import java.util.List;

public class StuwithSubList implements Serializable {
    private List<StuwithSub> stuwithLst;

    public List<StuwithSub> getStuwithLst() {
        return stuwithLst;
    }

    public void setStuwithLst(List<StuwithSub> stuwithLst) {
        this.stuwithLst = stuwithLst;
    }
}
