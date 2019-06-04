package com.example.studentams;

import java.util.Date;

public class CurrentSem {
    public static  String getCurrSemester(String batch) {
        String semester="";
        switch(batch) {
            case "2016-20":
                semester="Semester-6";
                break;
            case "2017-21":
                semester="Semester-4";
                break;
            case "2018-22":
                semester="Semester-2";
                break;
        }
        return semester;
    }
}
