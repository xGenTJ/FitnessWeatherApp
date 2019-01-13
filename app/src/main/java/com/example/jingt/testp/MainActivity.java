package com.example.jingt.testp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNextButton();
        configureNextButtonLogin();
    }

    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.apiNavButton);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ApiActivity.class));
            }
        });
    }
    private void configureNextButtonLogin(){
        Button nextButton = (Button) findViewById(R.id.loginNavButton);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
