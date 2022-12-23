package com.example.fukc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reset_pass extends AppCompatActivity {
    EditText pass,repass;
    Button btnverify;

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        pass=findViewById(R.id.passreset);
        repass=findViewById(R.id.repassreset);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        btnverify = (Button) findViewById(R.id.btnresetpass);
        db =new DBHelper(this);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vpass=pass.getText().toString();
                String vrepass=repass.getText().toString();

                if(vpass.equals(vrepass)){
                    db.updatepass(vpass,email);
                    Toast.makeText(Reset_pass.this, "Passwords Updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);}
                else{
                    Toast.makeText(Reset_pass.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}