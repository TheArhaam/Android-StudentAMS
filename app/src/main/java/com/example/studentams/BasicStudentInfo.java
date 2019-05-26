package com.example.studentams;

public class BasicStudentInfo {
    String FName;
    String LName;
    int age;
    String Branch;
    String Batch;
    String StudentID;

    public BasicStudentInfo(String FName, String LName, int age, String branch, String batch, String studentID) {
        this.FName = FName;
        this.LName = LName;
        this.age = age;
        Branch = branch;
        Batch = batch;
        StudentID = studentID;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public int getAge() {
        return age;
    }

    public String getBranch() {
        return Branch;
    }

    public String getBatch() {
        return Batch;
    }

    public String getStudentID() {
        return StudentID;
    }
}
