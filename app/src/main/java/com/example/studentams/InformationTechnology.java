package com.example.studentams;

public class InformationTechnology {

    Semester1 sem1;
    Semester2 sem2;
    Semester3 sem3;
    Semester4 sem4;
    Semester5 sem5;
    Semester6 sem6;
    Semester7 sem7;
    Semester8 sem8;

    public class Semester1 {
        Attendance EM1;
        Attendance BME;
        Attendance ECE;
        Attendance Physics;
        Attendance CP;

        public Semester1() {
            EM1 = new Attendance();
            BME = new Attendance();
            ECE = new Attendance();
            Physics = new Attendance();
            CP = new Attendance();
        }
    }
    public class Semester2 {
        Attendance EM2;
        Attendance BEEE;
        Attendance EM;
        Attendance Chemistry;
        Attendance EG;
        Attendance CS;

        public Semester2() {
            EM2 = new Attendance();
            BEEE = new Attendance();
            EM = new Attendance();
            Chemistry = new Attendance();
            EG = new Attendance();
            CS = new Attendance();
        }
    }
    public class Semester3 {
        Attendance DS;
        Attendance PP;
        Attendance FDS;
        Attendance CO;
        Attendance DEL;
        Attendance LA;

        public Semester3() {
            DS = new Attendance();
            PP = new Attendance();
            FDS = new Attendance();
            CO = new Attendance();
            DEL = new Attendance();
            LA = new Attendance();
        }
    }
    public class Semester4 {
        Attendance AM;
        Attendance OS;
        Attendance JWT;
        Attendance SE;
        Attendance DS;
        Attendance MT;

        public Semester4() {
            AM = new Attendance();
            OS = new Attendance();
            JWT = new Attendance();
            SE = new Attendance();
            DS = new Attendance();
            MT = new Attendance();

        }
    }
    public class Semester5 {
        Attendance DBMS;
        Attendance TOC;
        Attendance CN;
        Attendance IT;
        Attendance OR;
        Attendance BAF;
        Attendance QR;
        Attendance ES;

        public Semester5() {
            DBMS = new Attendance();
            TOC = new Attendance();
            CN = new Attendance();
            IT = new Attendance();
            OR = new Attendance();
            BAF = new Attendance();
            QR = new Attendance();
            ES = new Attendance();
        }
    }
    public class Semester6 {
        Attendance DSP;
        Attendance BI;
        Attendance PM;

        public Semester6() {
            DSP = new Attendance();
            BI = new Attendance();
            PM = new Attendance();
        }
    }
    public class Semester7 {
        Attendance DSRM;
        Attendance SP;
        Attendance OOAD;
        Attendance MC;
        Attendance CGM;

        public Semester7() {
            DSRM = new Attendance();
            SP = new Attendance();
            OOAD = new Attendance();
            MC = new Attendance();
            CGM = new Attendance();
        }
    }
    public class Semester8 {
        Attendance SOA;
        Attendance ADBMS;
        Attendance STQA;

        public Semester8() {
            SOA = new Attendance();
            ADBMS = new Attendance();
            STQA = new Attendance();
        }
    }

    public InformationTechnology() {
        sem1 = new Semester1();
        sem2 = new Semester2();
        sem3 = new Semester3();
        sem4 = new Semester4();
        sem5 = new Semester5();
        sem6 = new Semester6();
        sem7 = new Semester7();
        sem8 = new Semester8();
    }
}
