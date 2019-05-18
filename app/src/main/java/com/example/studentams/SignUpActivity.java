package com.example.studentams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class SignUpActivity extends AppCompatActivity {
    Button submit;
    StaffInfo sInfo;
    EditText et1,et2,et3,et4,et5,et6;
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

    //ENCRYPTION
    private String encrypt(String Data, String str) throws Exception{
        String AES = "AES";
        SecretKeySpec key = generateKey(str);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }
    private SecretKeySpec generateKey(String str) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = str.getBytes();
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }

}
