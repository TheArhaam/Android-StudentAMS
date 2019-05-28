package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.studentams.EncDecClass.encrypt;

public class AddStudent extends AppCompatActivity {
    EditText etfname,etlname,etage,etstudentid,etpassword;
    TextView tvuname,tvstudentidprebatch,tvstudentidprebranch;
    Spinner sbranch,sbatch;
    StudentInfo sInfo;
    Button submit;
    String username,studentidbatch,studentidbranch,studentid,pass;
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
    DatabaseReference attendanceDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etfname = findViewById(R.id.editText9);
        etlname = findViewById(R.id.editText10);
        etage = findViewById(R.id.editText11);
        tvstudentidprebatch = findViewById(R.id.textView21);
        tvstudentidprebranch = findViewById(R.id.textView23);
        etstudentid = findViewById(R.id.editText13);
        sbranch = findViewById(R.id.spinner2);
        sbatch = findViewById(R.id.spinner3);
        etpassword = findViewById(R.id.editText12);
        tvuname = findViewById(R.id.textView19);
        submit = findViewById(R.id.button4);

        studentidbatch = "1519";
        studentidbranch = "IT";
        tvstudentidprebatch.setText(studentidbatch);
        tvstudentidprebranch.setText(studentidbranch);

        //For generating prefix of Student ID
        sbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(sbatch.getSelectedItem().toString()) {
                    case "2015-19":
                        studentidbatch="1519";
                        break;
                    case "2016-20":
                        studentidbatch="1620";
                        break;
                    case "2017-21":
                        studentidbatch="1721";
                        break;
                    case "2018-22":
                        studentidbatch="1822";
                        break;
                    case "2019-23":
                        studentidbatch="1923";
                        break;
                }
                tvstudentidprebatch.setText(studentidbatch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvstudentidprebranch.setText(returnShortBranch(sbranch.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



//        etstudentid.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                setUname(etfname.getText().toString(),etstudentid.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //For generating username
        etstudentid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setUname(sbatch.getSelectedItem().toString(),sbranch.getSelectedItem().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Clicking the SUBMIT button, Submitting the information to firebase
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ENCRYPTION
                try {
                    pass = encrypt(etpassword.getText().toString(),username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                studentid = studentidbatch + studentidbranch + etstudentid.getText().toString();
                sInfo = new StudentInfo(etfname.getText().toString(),
                        etlname.getText().toString(),
                        Integer.valueOf(etage.getText().toString()),
                        sbranch.getSelectedItem().toString(),
                        sbatch.getSelectedItem().toString(),
                        pass,
                        studentid,
                        username);
                studentDB.child(studentid).setValue(sInfo);

                //ATTENDANCE INITIALIZATION
                attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(sbranch.getSelectedItem().toString()).child(studentid);
                //For Information Technology
                if(sbranch.getSelectedItem().toString().equals("Information Technology")) {
                    //SEMESTER-1
                    attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                    attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                    attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                    attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                    attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                    //SEMESTER-2
                    attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                    attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                    attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                    attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                    attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                    //SEMESTER-3
                    attendanceDB.child("Semester-3").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-3").child("PP").setValue(new Attendance("PP"));
                    attendanceDB.child("Semester-3").child("FDS").setValue(new Attendance("FDS"));
                    attendanceDB.child("Semester-3").child("CO").setValue(new Attendance("CO"));
                    attendanceDB.child("Semester-3").child("DEL").setValue(new Attendance("DEL"));
                    attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                    //SEMESTER-4
                    attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                    attendanceDB.child("Semester-4").child("OS").setValue(new Attendance("OS"));
                    attendanceDB.child("Semester-4").child("JWT").setValue(new Attendance("JWT"));
                    attendanceDB.child("Semester-4").child("SE").setValue(new Attendance("SE"));
                    attendanceDB.child("Semester-4").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-4").child("MT").setValue(new Attendance("MT"));
                    //SEMESTER-5
                    attendanceDB.child("Semester-5").child("DBMS").setValue(new Attendance("DBMS"));
                    attendanceDB.child("Semester-5").child("TOC").setValue(new Attendance("TOC"));
                    attendanceDB.child("Semester-5").child("CN").setValue(new Attendance("CN"));
                    attendanceDB.child("Semester-5").child("IT").setValue(new Attendance("IT"));
                    attendanceDB.child("Semester-5").child("OR").setValue(new Attendance("OR"));
                    attendanceDB.child("Semester-5").child("BAF").setValue(new Attendance("BAF"));
                    attendanceDB.child("Semester-5").child("QR").setValue(new Attendance("QR"));
                    attendanceDB.child("Semester-5").child("ES").setValue(new Attendance("ES"));
                    //SEMESTER-6
                    attendanceDB.child("Semester-6").child("DSP").setValue(new Attendance("DSP"));
                    attendanceDB.child("Semester-6").child("BI").setValue(new Attendance("BI"));
                    attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                    //SEMESTER-7
                    attendanceDB.child("Semester-7").child("DSRM").setValue(new Attendance("DSRM"));
                    attendanceDB.child("Semester-7").child("SP").setValue(new Attendance("SP"));
                    attendanceDB.child("Semester-7").child("OOAD").setValue(new Attendance("OOAD"));
                    attendanceDB.child("Semester-7").child("MC").setValue(new Attendance("MC"));
                    attendanceDB.child("Semester-7").child("CGM").setValue(new Attendance("CGM"));
                    //SEMESTER-8
                    attendanceDB.child("Semester-8").child("SOA").setValue(new Attendance("SOA"));
                    attendanceDB.child("Semester-8").child("ADBMS").setValue(new Attendance("ADBMS"));
                    attendanceDB.child("Semester-8").child("STQA").setValue(new Attendance("STQA"));
                }


                Toast.makeText(AddStudent.this,"Student added successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private String returnShortBranch(String longBranch) {
        String shortBranch="";
        switch(longBranch) {
            case "Information Technology":
                shortBranch="IT";
                break;
            case "Computer Science":
                shortBranch="CS";
                break;
            case "Mechanical":
                shortBranch="MECH";
                break;
            case "Civil":
                shortBranch="CIVIL";
                break;
        }
        return shortBranch;
    }

    //For setting the username
    public void setUname(String batch, String branch){
        username=tvstudentidprebatch.getText().toString() + tvstudentidprebranch.getText().toString() + etstudentid.getText().toString();
        tvuname.setText(username);
    }
}
