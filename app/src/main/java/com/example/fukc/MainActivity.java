package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText username, password;

    TextView signup,forgetpass;
    Button btnlogin;
    ImageView hide_login_password;
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
        signup = (TextView) findViewById(R.id.signupText);
        forgetpass = (TextView) findViewById(R.id.forgetpass);
        createNotificationChannel();

        hide_login_password = (ImageView) findViewById(R.id.hideloginpaswd);
        db = new DBHelper(this);
        //For keeping user logged in
        sp = getSharedPreferences("login", MODE_PRIVATE);

        if (sp.getBoolean("logged", true)) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        btnlogin.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                Toast.makeText(MainActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
            else {
                Boolean checkuserpass = db.checkusernamepassword(user, pass);
                if (checkuserpass == true) {
                    //For keeping user logged in
                    sp.edit().putBoolean("logged", true).apply();

                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {

                    Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });
        signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Register_user.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        forgetpass.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Forget_pass.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        //code for hide and unhide password in login ui
        hide_login_password.setImageResource(R.drawable.hidepaswd);
        hide_login_password.setOnClickListener(v -> {
            //first to check whether the password  is visible or hidden when the eye icon is clicked
            //Transformation method will return whether the password is hidden or visible
            if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                //if password is visible then hide it
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hide_login_password.setImageResource(R.drawable.hidepaswd);
            } else {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hide_login_password.setImageResource(R.drawable.unhidepswd);
            }
        });
        db.close();

    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="HabitTrackerChannel"; ///channel for habit tracker
            String description="Channel for Alaram Manager";
            int importance=NotificationManager.IMPORTANCE_HIGH;//It will appear on screen of a user
            NotificationChannel channel=new NotificationChannel("HabitTracker",name,importance);
                channel.setDescription(description);
                NotificationManager notificationManager=getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
        }
    }

}

