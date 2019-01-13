package com.example.jingt.testp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    ArrayList <WorkOut> FitnessDataArray = FetchData.getFitnessDataArray();
    ArrayList <TemperatureNode> WeatherDataArray = new ArrayList<>();
    TemperatureNode[] topTemp = new TemperatureNode[5];
    TemperatureNode[] lowTemp = new TemperatureNode[5];
    WorkOut[] topSpeed = new WorkOut[5];
    WorkOut[] lowSpeed = new WorkOut[5];





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        configureBackButton();

        //loop to fetch every weather day corresponding to every day he did fitness
        for(int i = 0; i < FitnessDataArray.size();i++){
            // WeatherDataArray[i] = fetchTemperature(FitnessdataArray[i].getDate); // PSEUDO CODE
        }


    }


    private void configureBackButton(){

        Button backToHome = (Button) findViewById(R.id.backToHome);
        backToHome.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                startActivity(new Intent(AnalysisActivity.this, HomeActivity.class));
            }
        });
    }
}