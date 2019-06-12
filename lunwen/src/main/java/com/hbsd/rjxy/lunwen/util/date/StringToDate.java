package com.hbsd.rjxy.lunwen.util.date;


import java.sql.Timestamp;
import java.util.Date;

public class StringToDate {
    public final static Timestamp getDate() {
        Date date = new Date();
        Timestamp ctime = new Timestamp(date.getTime());
        return ctime;
    }
}
