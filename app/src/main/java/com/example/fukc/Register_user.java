package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register_user extends AppCompatActivity {
    EditText username, password, repassword,email,securityan;
    Button signup;
    TextView signin;
    Spinner securityquel;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Retrieving register details in variables
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        repassword =  findViewById(R.id.repassword);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.signinText);
        email=findViewById(R.id.email);
        //email format validation

        // Set an OnFocusChangeListener for the EditText field
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    // Get the email address from the EditText field
                    String stremail = email.getText().toString().trim();

                    // Check if the email address is empty
                    if (stremail.isEmpty()) {
                        email.setError("Email is required");
                        return;
                    }

                    // Check if the email address is valid
                    if (!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
                        email.setError("Please enter a valid email");
                        return;
                    }


                }
            }
        });

        securityquel = findViewById(R.id.securityques);

        securityan = findViewById(R.id.securityans);
        db=new DBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user =username.getText().toString();
                String pass=password.getText().toString();
                String repass=repassword.getText().toString();
                String securityque = (String) securityquel.getSelectedItem();
                String emaill =email.getText().toString();
                String securityans= securityan.getText().toString();
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
                            Boolean insert =db.insertData(user,emaill,pass,securityque,securityans);
                            if (insert==true)
                            {
                                Toast.makeText(Register_user.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
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

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}