package com.example.studentams;

import android.content.Context;
import android.widget.Toast;

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

    public static  String getCurrSemesterShort(String batch) {
        String semester="";
        switch(batch) {
            case "1620":
                semester="Semester-6";
                break;
            case "1721":
                semester="Semester-4";
                break;
            case "1822":
                semester="Semester-2";
                break;
        }
        return semester;
    }
}
