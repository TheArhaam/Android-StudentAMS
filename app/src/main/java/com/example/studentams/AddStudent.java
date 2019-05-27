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
    DatabaseReference attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance");

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
                switch(sbranch.getSelectedItem().toString()) {
                    case "Information Technology":
                        studentidbranch="IT";
                        break;
                    case "Computer Science":
                        studentidbranch="CS";
                        break;
                    case "Mechanical":
                        studentidbranch="MECH";
                        break;
                    case "Civil":
                        studentidbranch="CIVIL";
                        break;
                }
                tvstudentidprebranch.setText(studentidbranch);
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
                sInfo = new StudentInfo(etfname.getText().toString(),etlname.getText().toString(),Integer.valueOf(etage.getText().toString()),sbranch.getSelectedItem().toString(),sbatch.getSelectedItem().toString(),pass,studentid,username);
                studentDB.child(studentid).setValue(sInfo);
                InformationTechnology it = new InformationTechnology();
                //works, implement using StudentID as foreignkey
                attendanceDB.child("Information Technology").child("Semester-1").child("EM-1").setValue(it.sem1.EM1);
                Toast.makeText(AddStudent.this,"Student added successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //For setting the username
    public void setUname(String batch, String branch){
        username=tvstudentidprebatch.getText().toString() + tvstudentidprebranch.getText().toString() + etstudentid.getText().toString();
        tvuname.setText(username);
    }
}
