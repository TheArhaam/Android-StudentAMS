package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {
    EditText etfname,etlname,etage,etstudentid,etpassword;
    TextView tvuname;
    Spinner sbranch,sbatch;
    StudentInfo sInfo;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etfname = findViewById(R.id.editText9);
        etlname = findViewById(R.id.editText10);
        etage = findViewById(R.id.editText11);
        etstudentid = findViewById(R.id.editText13);
        sbranch = findViewById(R.id.spinner2);
        sbatch = findViewById(R.id.spinner3);
        etpassword = findViewById(R.id.editText12);
        tvuname = findViewById(R.id.textView19);
        submit = findViewById(R.id.button4);

        if(etstudentid.hasFocus()) {
            tvuname.setText(etfname.getText().toString() + etstudentid.getText().toString());
            Toast.makeText(getApplicationContext(),"FOCUS",Toast.LENGTH_LONG).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
