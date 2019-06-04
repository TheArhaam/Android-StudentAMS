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
                    attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-2
                    attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                    attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                    attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                    attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                    attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                    attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-3
                    attendanceDB.child("Semester-3").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-3").child("PP").setValue(new Attendance("PP"));
                    attendanceDB.child("Semester-3").child("FDS").setValue(new Attendance("FDS"));
                    attendanceDB.child("Semester-3").child("CO").setValue(new Attendance("CO"));
                    attendanceDB.child("Semester-3").child("DEL").setValue(new Attendance("DEL"));
                    attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                    attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-4
                    attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                    attendanceDB.child("Semester-4").child("OS").setValue(new Attendance("OS"));
                    attendanceDB.child("Semester-4").child("JWT").setValue(new Attendance("JWT"));
                    attendanceDB.child("Semester-4").child("SE").setValue(new Attendance("SE"));
                    attendanceDB.child("Semester-4").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-4").child("MT").setValue(new Attendance("MT"));
                    attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-5
                    attendanceDB.child("Semester-5").child("DBMS").setValue(new Attendance("DBMS"));
                    attendanceDB.child("Semester-5").child("TOC").setValue(new Attendance("TOC"));
                    attendanceDB.child("Semester-5").child("CN").setValue(new Attendance("CN"));
                    attendanceDB.child("Semester-5").child("IT").setValue(new Attendance("IT"));
                    attendanceDB.child("Semester-5").child("OR").setValue(new Attendance("OR"));
                    attendanceDB.child("Semester-5").child("BAF").setValue(new Attendance("BAF"));
                    attendanceDB.child("Semester-5").child("QR").setValue(new Attendance("QR"));
                    attendanceDB.child("Semester-5").child("ES").setValue(new Attendance("ES"));
                    attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-6
                    attendanceDB.child("Semester-6").child("DSP").setValue(new Attendance("DSP"));
                    attendanceDB.child("Semester-6").child("BI").setValue(new Attendance("BI"));
                    attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                    attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-7
                    attendanceDB.child("Semester-7").child("DSRM").setValue(new Attendance("DSRM"));
                    attendanceDB.child("Semester-7").child("SP").setValue(new Attendance("SP"));
                    attendanceDB.child("Semester-7").child("OOAD").setValue(new Attendance("OOAD"));
                    attendanceDB.child("Semester-7").child("MC").setValue(new Attendance("MC"));
                    attendanceDB.child("Semester-7").child("CGM").setValue(new Attendance("CGM"));
                    attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-8
                    attendanceDB.child("Semester-8").child("SOA").setValue(new Attendance("SOA"));
                    attendanceDB.child("Semester-8").child("ADBMS").setValue(new Attendance("ADBMS"));
                    attendanceDB.child("Semester-8").child("STQA").setValue(new Attendance("STQA"));
                    attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
                }
                //For Computer Science
                else if(sbranch.getSelectedItem().toString().equals("Computer Science")) {
                    //SEMESTER-1
                    attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                    attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                    attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                    attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                    attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                    attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-2
                    attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                    attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                    attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                    attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                    attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                    attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-3
                    attendanceDB.child("Semester-3").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-3").child("PP").setValue(new Attendance("PP"));
                    attendanceDB.child("Semester-3").child("FDS").setValue(new Attendance("FDS"));
                    attendanceDB.child("Semester-3").child("CO").setValue(new Attendance("CO"));
                    attendanceDB.child("Semester-3").child("DEL").setValue(new Attendance("DEL"));
                    attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                    attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-4
                    attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                    attendanceDB.child("Semester-4").child("OS").setValue(new Attendance("OS"));
                    attendanceDB.child("Semester-4").child("JWT").setValue(new Attendance("JWT"));
                    attendanceDB.child("Semester-4").child("SE").setValue(new Attendance("SE"));
                    attendanceDB.child("Semester-4").child("DS").setValue(new Attendance("DS"));
                    attendanceDB.child("Semester-4").child("MT").setValue(new Attendance("MT"));
                    attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-5
                    attendanceDB.child("Semester-5").child("DBMS").setValue(new Attendance("DBMS"));
                    attendanceDB.child("Semester-5").child("TOC").setValue(new Attendance("TOC"));
                    attendanceDB.child("Semester-5").child("CN").setValue(new Attendance("CN"));
                    attendanceDB.child("Semester-5").child("IT").setValue(new Attendance("IT"));
                    attendanceDB.child("Semester-5").child("OR").setValue(new Attendance("OR"));
                    attendanceDB.child("Semester-5").child("DAA").setValue(new Attendance("DAA"));
                    attendanceDB.child("Semester-5").child("BAF").setValue(new Attendance("BAF"));
                    attendanceDB.child("Semester-5").child("QR").setValue(new Attendance("QR"));
                    attendanceDB.child("Semester-5").child("ES").setValue(new Attendance("ES"));
                    attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-6
                    attendanceDB.child("Semester-6").child("DSP").setValue(new Attendance("DSP"));
                    attendanceDB.child("Semester-6").child("BI").setValue(new Attendance("BI"));
                    attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                    attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-7
                    attendanceDB.child("Semester-7").child("DSRM").setValue(new Attendance("DSRM"));
                    attendanceDB.child("Semester-7").child("SP").setValue(new Attendance("SP"));
                    attendanceDB.child("Semester-7").child("OOAD").setValue(new Attendance("OOAD"));
                    attendanceDB.child("Semester-7").child("MC").setValue(new Attendance("MC"));
                    attendanceDB.child("Semester-7").child("NS").setValue(new Attendance("NS"));
                    attendanceDB.child("Semester-7").child("CGM").setValue(new Attendance("CGM"));
                    attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-8
                    attendanceDB.child("Semester-8").child("CC").setValue(new Attendance("CC"));
                    attendanceDB.child("Semester-8").child("ADBMS").setValue(new Attendance("ADBMS"));
                    attendanceDB.child("Semester-8").child("STQA").setValue(new Attendance("STQA"));
                    attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
                }
                //For Mechanical
                else if(sbranch.getSelectedItem().toString().equals("Mechanical")) {
                    //SEMESTER-1
                    attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                    attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                    attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                    attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                    attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                    attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-2
                    attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                    attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                    attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                    attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                    attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                    attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-3
                    attendanceDB.child("Semester-3").child("AM").setValue(new Attendance("AM"));
                    attendanceDB.child("Semester-3").child("SOM").setValue(new Attendance("SOM"));
                    attendanceDB.child("Semester-3").child("MAM").setValue(new Attendance("MAM"));
                    attendanceDB.child("Semester-3").child("TOM-1").setValue(new Attendance("TOM-1"));
                    attendanceDB.child("Semester-3").child("MT").setValue(new Attendance("MT"));
                    attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-4
                    attendanceDB.child("Semester-4").child("FM").setValue(new Attendance("FM"));
                    attendanceDB.child("Semester-4").child("MD-1").setValue(new Attendance("MD-1"));
                    attendanceDB.child("Semester-4").child("HT").setValue(new Attendance("HT"));
                    attendanceDB.child("Semester-4").child("MS").setValue(new Attendance("MS"));
                    attendanceDB.child("Semester-4").child("TOM-2").setValue(new Attendance("TOM-2"));
                    attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-5
                    attendanceDB.child("Semester-5").child("ICE").setValue(new Attendance("ICE"));
                    attendanceDB.child("Semester-5").child("PM").setValue(new Attendance("PM"));
                    attendanceDB.child("Semester-5").child("MD-2").setValue(new Attendance("MD-2"));
                    attendanceDB.child("Semester-5").child("CDCM").setValue(new Attendance("CDCM"));
                    attendanceDB.child("Semester-5").child("FM").setValue(new Attendance("FM"));
                    attendanceDB.child("Semester-5").child("MT").setValue(new Attendance("MT"));
                    attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-6
                    attendanceDB.child("Semester-6").child("NCPP").setValue(new Attendance("NCPP"));
                    attendanceDB.child("Semester-6").child("PVD").setValue(new Attendance("PVD"));
                    attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                    attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-7
                    attendanceDB.child("Semester-7").child("OR").setValue(new Attendance("OR"));
                    attendanceDB.child("Semester-7").child("TQM").setValue(new Attendance("TQM"));
                    attendanceDB.child("Semester-7").child("ES").setValue(new Attendance("ES"));
                    attendanceDB.child("Semester-7").child("MSD").setValue(new Attendance("MSD"));
                    attendanceDB.child("Semester-7").child("LA").setValue(new Attendance("LA"));
                    attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-8
                    attendanceDB.child("Semester-8").child("FEM").setValue(new Attendance("FEM"));
                    attendanceDB.child("Semester-8").child("RAC").setValue(new Attendance("RAC"));
                    attendanceDB.child("Semester-8").child("IAR").setValue(new Attendance("IAR"));
                    attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
                }
                //For Civil
                else if(sbranch.getSelectedItem().toString().equals("Civil")) {
                    //SEMESTER-1
                    attendanceDB.child("Semester-1").child("EM-1").setValue(new Attendance("EM-1"));
                    attendanceDB.child("Semester-1").child("BME").setValue(new Attendance("BME"));
                    attendanceDB.child("Semester-1").child("ECE").setValue(new Attendance("ECE"));
                    attendanceDB.child("Semester-1").child("Physics").setValue(new Attendance("Physics"));
                    attendanceDB.child("Semester-1").child("CP").setValue(new Attendance("CP"));
                    attendanceDB.child("Semester-1").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-1").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-1").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-2
                    attendanceDB.child("Semester-2").child("EM-2").setValue(new Attendance("EM-2"));
                    attendanceDB.child("Semester-2").child("BEEE").setValue(new Attendance("BEEE"));
                    attendanceDB.child("Semester-2").child("EM").setValue(new Attendance("EM"));
                    attendanceDB.child("Semester-2").child("Chemistry").setValue(new Attendance("Chemistry"));
                    attendanceDB.child("Semester-2").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-2").child("CS").setValue(new Attendance("CS"));
                    attendanceDB.child("Semester-2").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-2").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-2").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-3
                    attendanceDB.child("Semester-3").child("SM").setValue(new Attendance("SM"));
                    attendanceDB.child("Semester-3").child("FM-1").setValue(new Attendance("FM-1"));
                    attendanceDB.child("Semester-3").child("CT").setValue(new Attendance("CT"));
                    attendanceDB.child("Semester-3").child("BCM").setValue(new Attendance("BCM"));
                    attendanceDB.child("Semester-3").child("EG").setValue(new Attendance("EG"));
                    attendanceDB.child("Semester-3").child("LA").setValue(new Attendance("LA"));
                    attendanceDB.child("Semester-3").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-3").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-3").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-4
                    attendanceDB.child("Semester-4").child("AM").setValue(new Attendance("AM"));
                    attendanceDB.child("Semester-4").child("SA-1").setValue(new Attendance("SA-1"));
                    attendanceDB.child("Semester-4").child("BDD").setValue(new Attendance("BDD"));
                    attendanceDB.child("Semester-4").child("FM-2").setValue(new Attendance("FM-2"));
                    attendanceDB.child("Semester-4").child("SV-1").setValue(new Attendance("SV-1"));
                    attendanceDB.child("Semester-4").child("ES").setValue(new Attendance("ES"));
                    attendanceDB.child("Semester-4").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-4").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-4").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-5
                    attendanceDB.child("Semester-5").child("SA-2").setValue(new Attendance("SA-2"));
                    attendanceDB.child("Semester-5").child("TE-1").setValue(new Attendance("TE-1"));
                    attendanceDB.child("Semester-5").child("QSV").setValue(new Attendance("QSV"));
                    attendanceDB.child("Semester-5").child("EE-1").setValue(new Attendance("EE-1"));
                    attendanceDB.child("Semester-5").child("SD-1").setValue(new Attendance("SD-1"));
                    attendanceDB.child("Semester-5").child("SV-2").setValue(new Attendance("SV-2"));
                    attendanceDB.child("Semester-5").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-5").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-5").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-6
                    attendanceDB.child("Semester-6").child("WPE").setValue(new Attendance("WPE"));
                    attendanceDB.child("Semester-6").child("ES").setValue(new Attendance("ES"));
                    attendanceDB.child("Semester-6").child("PM").setValue(new Attendance("PM"));
                    attendanceDB.child("Semester-6").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-6").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-6").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-7
                    attendanceDB.child("Semester-7").child("EE-2").setValue(new Attendance("EE-2"));
                    attendanceDB.child("Semester-7").child("GE-1").setValue(new Attendance("GE-1"));
                    attendanceDB.child("Semester-7").child("CTM").setValue(new Attendance("CTM"));
                    attendanceDB.child("Semester-7").child("SD-2").setValue(new Attendance("SD-2"));
                    attendanceDB.child("Semester-7").child("TE-2").setValue(new Attendance("TE-2"));
                    attendanceDB.child("Semester-7").child("WRE").setValue(new Attendance("WRE"));
                    attendanceDB.child("Semester-7").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-7").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-7").child("SemPercentage").setValue((float) 0);
                    //SEMESTER-8
                    attendanceDB.child("Semester-8").child("GE-2").setValue(new Attendance("GE-2"));
                    attendanceDB.child("Semester-8").child("SD-3").setValue(new Attendance("SD-3"));
                    attendanceDB.child("Semester-8").child("DHS").setValue(new Attendance("DHS"));
                    attendanceDB.child("Semester-8").child("SemAttended").setValue(0);
                    attendanceDB.child("Semester-8").child("SemAttendance").setValue(0);
                    attendanceDB.child("Semester-8").child("SemPercentage").setValue((float) 0);
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
