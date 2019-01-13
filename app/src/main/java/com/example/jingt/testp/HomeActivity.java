package com.example.jingt.testp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    ImageButton fitnessPageButton;
    ImageButton weatherPageButton;
    ImageButton analysisPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        configureFitnessButton();
        configureWeatherButton();
        configureAnalysisButton();

    }

    void configureFitnessButton() {
        fitnessPageButton = (ImageButton) findViewById(R.id.fitnessPageButton);
        fitnessPageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ConnectToEndomondo.class));
            }
        });
    }

    void configureWeatherButton() {
        weatherPageButton = (ImageButton) findViewById(R.id.weatherPageButton);
        weatherPageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ApiActivity.class));
            }
        });
    }

    void configureAnalysisButton() {
        analysisPageButton = (ImageButton) findViewById(R.id.analysisPageButton);
        analysisPageButton.setOnClickListener
                (new View.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(new Intent(HomeActivity.this, AnalysisActivity.class));
                    }
                });
    }

}



