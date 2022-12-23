package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    TextView signup,forgetpass;
    Button btnlogin;
    DBHelper db;
    //For keeping user logged in
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        signup=findViewById(R.id.signupText);
        forgetpass=findViewById(R.id.forgetpass);
        db =new DBHelper(this);
        //For keeping user logged in
        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",true)){
            Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
            startActivity(intent);
        }


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) )
                    Toast.makeText(MainActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else
                {
                    Boolean checkuserpass=db.checkusernamepassword(user,pass);
                    if (checkuserpass==true)
                    {
                        //For keeping user logged in
                        sp.edit().putBoolean("logged",true).apply();

                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), Register_user.class);
                startActivity(intent);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), Forget_pass.class);
                startActivity(intent);
            }
        });
    }}

