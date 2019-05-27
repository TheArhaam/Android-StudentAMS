package com.example.studentams;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static com.example.studentams.EncDecClass.decrypt;


public class MainActivity extends AppCompatActivity {
    EditText et1,et2,et3;
//    EditText et4;
    Button SignUpButton,LoginButton;
    RadioGroup SSRadioGroup;
    RadioButton SSRadioButton;
    Toolbar toolbar;
    DatabaseReference staffDB = FirebaseDatabase.getInstance().getReference("StaffInfo");


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (android.widget.Toolbar) findViewById(R.id.app_bar);
        setActionBar(toolbar);

        et1= findViewById(R.id.editText3); //Username
        et2= findViewById(R.id.editText5); //Password
//        et4=findViewById(R.id.editText9); //Rough display


        //Student or Staff RadioGroup
        SSRadioGroup = findViewById(R.id.RadioGroup1);

        //Login Button
        LoginButton = (Button) findViewById(R.id.button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(et1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_SHORT).show();
                }
                else {

                    String str=checkSSRadioGroup();
                    if(str.equals("Staff")) {
                        staffDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                checkAuthorization(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        //SignUp Button
        SignUpButton = (Button) findViewById(R.id.button2);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str=checkSSRadioGroup();
                if(str.equals("Staff")){
                    openSignUpActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"SignUp is only available for STAFF",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Function to Check Username
    public void checkAuthorization(DataSnapshot dataSnapshot) {
        String rUName="";
        String rPass="";
        int count= (int) dataSnapshot.getChildrenCount();
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            rUName=ds.child("UName").getValue().toString();
            rPass=ds.child("SPassword").getValue().toString();
            String decPass = "";

            //DECRYPTING
            try {
                decPass = decrypt(rPass,rUName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Checking Username and Password
            if(rUName.equals(et1.getText().toString()) && decPass.equals(et2.getText().toString())) {
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                openStaffLoggedInActivity(rUName);
                break;
            }
            count--;
        }
        if(count==0) {
            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
        }
    }

    //DECRYPTION
//    private String decrypt(String data, String str) throws Exception{
//        String AES = "AES";
//        SecretKeySpec key = generateKey(str);
//        Cipher c = Cipher.getInstance(AES);
//        c.init(Cipher.DECRYPT_MODE,key);
//        byte[] decodedValue = Base64.decode(data,Base64.DEFAULT);
//        byte[] decValue = c.doFinal(decodedValue);
//        String decryptedValue = new String(decValue);
//        return decryptedValue;
//    }
//    private SecretKeySpec generateKey(String str) throws Exception{
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = str.getBytes();
//        digest.update(bytes,0,bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
//        return secretKeySpec;
//    }

    //Checking which checkbox is selected
    public String checkSSRadioGroup() {
        int RadioButtonID = SSRadioGroup.getCheckedRadioButtonId();
        SSRadioButton = findViewById(RadioButtonID);
        return (String) SSRadioButton.getText();

    }

    //Opens the SignUp Activity
    public void openSignUpActivity() {
        Intent intent = new Intent(this,SignUpActivity.class);
        this.recreate();
        startActivity(intent);
//        et2=findViewById(R.id.editText3);
//        et3=findViewById(R.id.editText5);
//        et2.setText("");
//        et3.setText("");
    }

    //Opens the StaffLoggedIn Activity
    public void openStaffLoggedInActivity(String username) {
        Intent intent = new Intent(this,StaffLoggedIn.class);
        intent.putExtra("username",username);
        this.recreate();
        startActivity(intent);
//        et2=findViewById(R.id.editText3);
//        et3=findViewById(R.id.editText5);
//        et2.setText("");
//        et3.setText("");
    }
}
