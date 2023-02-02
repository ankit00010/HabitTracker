package com.example.fukc.activityAndFragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

public class Forget_pass extends AppCompatActivity {
    EditText email;

    Button btnverify;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        email=findViewById(R.id.usernameverify);
        btnverify = (Button) findViewById(R.id.btnverify);
        db =new DBHelper(this);
        btnverify.setOnClickListener(view -> {

            String emaill=email.getText().toString();
            Boolean checkemail=db.email(emaill);

            if (checkemail==true)
            {
                Toast.makeText(Forget_pass.this, "Email verification successful", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), Forget_pass_2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("email", emaill);
                startActivity(intent);

            }
            else{
                Toast.makeText(Forget_pass.this, "No record found for provided email", Toast.LENGTH_SHORT).show();
            }
        });
        db.close();
    }
}
