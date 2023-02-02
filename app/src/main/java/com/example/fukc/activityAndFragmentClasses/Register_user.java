package com.example.fukc.activityAndFragmentClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

public class Register_user extends AppCompatActivity {
    EditText username, password, repassword,email,securityan;
    Button signup;
    TextView signin;
    Spinner securityquel ;
    DBHelper db;
    ImageView hidepaswd,hidecnfrmpswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        //Retrieving register details in variables

        username =  findViewById(R.id.username);
        password =  findViewById(R.id.passreset);
        repassword =  findViewById(R.id.repassreset);
        signup =  findViewById(R.id.btnsignup);
        signin =  findViewById(R.id.signinText);
        //varibales for hide and unhide paswword eye
        hidepaswd=findViewById(R.id.hidepaswd);
        hidecnfrmpswd=findViewById(R.id.hidepaswd1);

        email=findViewById(R.id.email);
        //email format validation

        // Set an OnFocusChangeListener for the EditText field
        email.setOnFocusChangeListener((v, hasFocus) -> {

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

                }


            }
        });

        securityquel = findViewById(R.id.securityques);

        securityan = findViewById(R.id.securityans);
        db=new DBHelper(this);
        signup.setOnClickListener(view -> {
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
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        });
        signin.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


        //Show hide password  using eye icon
        hidepaswd.setImageResource(R.drawable.hidepaswd);
        hidecnfrmpswd.setImageResource(R.drawable.hidepaswd);
        hidepaswd.setOnClickListener(v -> {
            //first to check whether the password  is visible or hidden when the eye icon is clicked
            //Transformation method will return whether the password is hidden or visible
            if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if password is visible then hide it
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hidepaswd.setImageResource(R.drawable.hidepaswd);
            }else
            {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidepaswd.setImageResource(R.drawable.unhidepswd);
            }
        });

        //code for confirm password hide and unhide
        hidecnfrmpswd.setOnClickListener(v -> {
            //first to check whether the password  is visible or hidden when the eye icon is clicked
            //Transformation method will return whether the password is hidden or visible
            if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if password is visible then hide it
                repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hidepaswd.setImageResource(R.drawable.hidepaswd);
            }else
            {
                repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidecnfrmpswd.setImageResource(R.drawable.unhidepswd);
            }
        });
        db.close();
    }
}