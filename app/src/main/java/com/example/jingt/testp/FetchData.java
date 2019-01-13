package com.example.jingt.testp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchData extends AppCompatActivity {

    Bundle bundle;          //to receive the token data from login controller intent
    String token;          //authentication data when calling endomondo workout api

    Button graph,fetch;
    TextView output;
    Call<WorkOutSet> call;

    static ArrayList <WorkOut> jsonData;  //this is what is passed to Airi

    Gson gson=new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        output = findViewById(R.id.textViewWorkouts);
        output.setMovementMethod(new ScrollingMovementMethod());
        graph=findViewById(R.id.graphData);
        fetch=findViewById(R.id.fetch_data);
        bundle = getIntent().getExtras();
        token = new String(bundle.getString("auth"));

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    try {
                        servicePost(token);
                    }
                    catch(Exception e){
                        Log.e("exception: ",e.getMessage());
                    }
                }
                else {
                    updateDisplay("Network Unavailable, Verify internet connection.");
                }
            }
        });

        graph.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              Intent intent = new Intent(FetchData.this,MainGraphing.class);
              //Bundle bundle = new Bundle();
             // bundle.putParcelableArrayList("workoutData",  jsonData);
                intent.putExtra("workoutData",jsonData);
              FetchData.this.startActivity(intent);
            }
        });

        updateDisplay("Login Successfull, token: "+token);
    }





    private void servicePost(String authToken) {
        Call<WorkOutSet> call=RetroClient
                .getRetroInstant()
                .getApi()
                .getWork(authToken);

        call.enqueue(new Callback<WorkOutSet>() {
            @Override
            public void onResponse(Call<WorkOutSet> call, Response<WorkOutSet> response) {
                if (response.isSuccessful()) {
                    try {
                        WorkOutSet recentWorkouts=response.body();
                       jsonData=recentWorkouts.getworkOutList();  //need to pass this to Airi
                        for (WorkOut w:jsonData){
                            if (w.getSport()==1||w.getSport()==2||w.getSport()==21)
                                updateDisplay("distance in KM: "+w.getDistanceKm());
                            updateDisplay("avg speed: "+w.getSpeedKmhAvg());
                        }
                        updateDisplay("Size is: "+jsonData.size());

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<WorkOutSet> call, Throwable t){
                updateDisplay(""+Log.d("failed",t.getMessage()));
            }
        });
    }


    private void updateDisplay(String message) {
        output.append(message+"\n");
    }

    private boolean isOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo!=null&&netInfo.isConnected()){

            return true;
        }
        else {
            return false;
        }
    }

    public static ArrayList<WorkOut> getFitnessDataArray(){
        return jsonData;
    }


}

