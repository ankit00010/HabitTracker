package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register_user extends AppCompatActivity {
    EditText username, password, repassword,email;
    Button signup;
    TextView signin;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        repassword =  findViewById(R.id.repassword);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.signinText);
        email=findViewById(R.id.email);
        db=new DBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user =username.getText().toString();
                String pass=password.getText().toString();
                String repass=repassword.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))
                {
                    Toast.makeText(Register_user.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (pass.equals(repass))
                    {
                        Boolean checkuser=db.checkusername(user);
                        if (checkuser==false)
                        {
                            Boolean insert =db.insertData(user,pass);
                            if (insert==true)
                            {
                                Toast.makeText(Register_user.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), Register_user.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Register_user.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Register_user.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register_user.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Register_user.class);
                startActivity(intent);
                finish();
            }
        });
    }
}