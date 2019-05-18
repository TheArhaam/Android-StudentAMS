package com.example.studentams;

public class StaffInfo {
    String FName;
    String LName;
    String UName;
    String StaffID;
    String SBranch;

    public StaffInfo() {

    }


    public StaffInfo(String FName, String LName, String UName, String staffID, String SBranch) {
        this.FName = FName;
        this.LName = LName;
        this.UName = UName;
        StaffID = staffID;
        this.SBranch = SBranch;
    }

}
