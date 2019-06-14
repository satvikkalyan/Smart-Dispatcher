package com.example.android.smartdispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button loginbutton= findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crrtmobileno="9492914041";
                String crrtpassword="12345678";

                EditText mobileno= findViewById(R.id.mobileno);
                EditText password= findViewById(R.id.password);

                if(mobileno.getText().toString().equals(crrtmobileno) && password.getText().toString().equals(crrtpassword))
                {
                    Intent intent=new Intent(login.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(login.this,"Enter Valid Credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
