package com.example.studentams;

public class BasicStudentInfo {
    String FName;
    String LName;
    int age;
    String Branch;
    String Batch;
    String StudentID;

    public BasicStudentInfo() {
    }

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

    public void setFName(String FName) {
        this.FName = FName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
}
