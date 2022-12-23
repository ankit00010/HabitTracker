package com.example.fukc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

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
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emaill=email.getText().toString();
                Boolean checkemail=db.email(emaill);

                if (checkemail==true)
                {
                    Toast.makeText(Forget_pass.this, "Email verification successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), Forget_pass2.class);
                    intent.putExtra("email", emaill);
                    startActivity(intent);

                }
                    else{
                    Toast.makeText(Forget_pass.this, "No record found for provided email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
