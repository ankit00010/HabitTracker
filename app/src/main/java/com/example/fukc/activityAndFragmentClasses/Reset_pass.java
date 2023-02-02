package com.example.fukc.activityAndFragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

public class Reset_pass extends AppCompatActivity {
    EditText pass,repass;
    Button btnverify;
    ImageView hide_reset_password,hide_reset_confirm_password;

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        pass=findViewById(R.id.passreset);
        repass=findViewById(R.id.repassreset);
        hide_reset_password=(ImageView) findViewById(R.id.hide_reset_paswd);
        hide_reset_confirm_password=(ImageView) findViewById(R.id.hide_reset_confirm_paswd);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        btnverify = (Button) findViewById(R.id.btnresetpass);
        db =new DBHelper(this);
        btnverify.setOnClickListener(view -> {
            String vpass=pass.getText().toString();
            String vrepass=repass.getText().toString();

            if(vpass.equals(vrepass)){
                db.updatepass(vpass,email);
                Toast.makeText(Reset_pass.this, "Passwords Updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent1 =new Intent(getApplicationContext(),MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);}
            else{
                Toast.makeText(Reset_pass.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            }


        });
        //code for hide and unhide reset password as well as reset confirm password
        //Show hide password  using eye icon
        hide_reset_password.setImageResource(R.drawable.hidepaswd);
        hide_reset_confirm_password.setImageResource(R.drawable.hidepaswd);
        hide_reset_password.setOnClickListener(v -> {
            //first to check whether the password  is visible or hidden when the eye icon is clicked
            //Transformation method will return whether the password is hidden or visible
            if(pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if password is visible then hide it
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide_reset_password.setImageResource(R.drawable.hidepaswd);
            }else
            {
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide_reset_password.setImageResource(R.drawable.unhidepswd);
            }
        });

        //code for confirm password hide and unhide
        hide_reset_confirm_password.setOnClickListener(v -> {
            //first to check whether the password  is visible or hidden when the eye icon is clicked
            //Transformation method will return whether the password is hidden or visible
            if(repass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if password is visible then hide it
                repass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide_reset_confirm_password.setImageResource(R.drawable.hidepaswd);
            }else
            {
                repass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide_reset_confirm_password.setImageResource(R.drawable.unhidepswd);
            }
        });
        db.close();
    }
}