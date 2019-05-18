package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
    Button submit;
    StaffInfo sInfo;
    EditText et1,et2,et3,et4,et5,et6;
    Spinner sp1;
    DatabaseReference staffDB = FirebaseDatabase.getInstance().getReference("StaffInfo");;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et1 = findViewById(R.id.editText4);
        et2 = findViewById(R.id.editText6);
        et3 = findViewById(R.id.editText7);
        et4 = findViewById(R.id.editText8);
        sp1 = findViewById(R.id.spinner);
        et5 = findViewById(R.id.editText);
        et6 = findViewById(R.id.editText2);
        submit= findViewById(R.id.button3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStaff();
            }
        });
    }

    private void addStaff() {
        String SID = staffDB.push().getKey();
        sInfo = new StaffInfo(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),sp1.getSelectedItem().toString());
        staffDB.child(SID).setValue(sInfo);
        Toast.makeText(this,"SignUp successful",Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
