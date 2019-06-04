package com.example.studentams;

import java.util.Date;

public class CurrentSem {
    public static  String getOddEvenSemester() {
        Date date = new Date();
        String semester = "";
        if(date.getMonth()>=7 && date.getMonth()<=12) {
            semester = "odd";
        }
        else if(date.getMonth()>=1 && date.getMonth()<=6) {
            semester = "even";
        }
        return semester;
    }
}
