package com.example.jingt.testp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //declaring variables
    private EditText username;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing variables
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        Button loginBtn = (Button)findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {
                    Toast.makeText(getApplicationContext(),"Logging in...",Toast.LENGTH_SHORT).show(); //pop-up
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class)); //log-in to HomeActivity
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Username/Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
