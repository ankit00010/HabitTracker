package com.example.fukc;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class Forget_pass2 extends AppCompatActivity {
    EditText ans;

    Button btnverify;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password2);
        ans=(EditText) findViewById(R.id.passverify);
        btnverify=(Button)findViewById(R.id.btnverify);
        db =new DBHelper(this);
        Intent intent = getIntent();
        String emaill = intent.getStringExtra("email");
        String que= db.giveque(emaill);
        TextView ques;
        ques = (TextView) findViewById(R.id.verifyque);
        ques.setText(que);


        btnverify.setOnClickListener(view -> {
            String anss=ans.getText().toString();
            String que1 = db.giveque(emaill);

            Boolean checksecurity=db.securityverify(que1,anss);

            if(checksecurity==true){
                Intent intent1 =new Intent(getApplicationContext(),Reset_pass.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("email", emaill);
                startActivity(intent1);}
            else{
                Toast.makeText(Forget_pass2.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
            }


        });
        db.close();
    }
}
