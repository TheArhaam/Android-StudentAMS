package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.studentams.EncDecClass.encrypt;


public class SignUpActivity extends AppCompatActivity {
    Button submit;
    StaffInfo sInfo;
    EditText et1,et2,et3,et4,et5,et6;
    TextView tv1;
    Spinner sp1;
    DatabaseReference staffDB = FirebaseDatabase.getInstance().getReference("StaffInfo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et1 = findViewById(R.id.editText4); //First Name
        et2 = findViewById(R.id.editText6); //Last Name
        et3 = findViewById(R.id.editText7); //Username
        et4 = findViewById(R.id.editText8); //StaffID
        sp1 = findViewById(R.id.spinner); //Branch
        et5 = findViewById(R.id.editText); //Password
        et6 = findViewById(R.id.editText2); //Confirm Password
        tv1 = findViewById(R.id.textView10);//Confirm Password
        submit= findViewById(R.id.button3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().isEmpty() ||
                        et2.getText().toString().isEmpty() ||
                        et3.getText().toString().isEmpty() ||
                        et4.getText().toString().isEmpty() ||
                        et5.getText().toString().isEmpty() ||
                        et6.getText().toString().isEmpty()  ) {
                    Toast.makeText(v.getContext(),"Insufficient Data",Toast.LENGTH_SHORT).show();
                }
                else if(!et5.getText().toString().equals(et6.getText().toString())) {
                    et5.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    et6.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    Toast.makeText(v.getContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et5.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                            et6.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                        }
                    },2000);

                }
                else {
                    addStaff();
                }
            }
        });
    }

    private void addStaff() {
        String SID = staffDB.push().getKey();
        String pass = "";

        //ENCRYPTION
        try {
            pass = encrypt(et5.getText().toString(),et3.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        sInfo = new StaffInfo(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),sp1.getSelectedItem().toString(),pass);
        staffDB.child(SID).setValue(sInfo);
        Toast.makeText(this,"SignUp successful",Toast.LENGTH_SHORT).show();
        this.finish();
    }

}
