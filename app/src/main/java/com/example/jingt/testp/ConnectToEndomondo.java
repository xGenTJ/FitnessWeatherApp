package com.example.jingt.testp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectToEndomondo extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private Button login;
    private TextView ouput;

    //declare variables specific to HTTP reqeust when calling Endo API
    private final String action="pair";
    private final String deviceId="";
    private final String country = "Canada";

    //authentication data needed when fetching/requesting workouts in subsequent calls to Endo API
    private String token;

    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_endomondo);

        name = findViewById(R.id.etEndoEmail);
        pass = findViewById(R.id.etEndoPass);
        login=  findViewById(R.id.btnEndoLogin);
        ouput= findViewById(R.id.textView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToEndo(name.getText().toString(),pass.getText().toString());
            }
        });
    }

    private void loginToEndo(String u, String p) {

        //handle network conectivity
        if (isOnline()) {
            try {
                token = serviceToken(u, p, deviceId, action, country);

            }
            catch(Exception e){
                Log.e("an exception occurred: ",e.getMessage());
            }
        }
        else{
            Toast.makeText(this, "Network Unavailble, " +
                    "verify that your phone has a conneciton to the " +
                    "internet.", Toast.LENGTH_SHORT).show();
        }

        if (token!=null){
           token = parseToken(token);
            Intent intent = new Intent(ConnectToEndomondo.this, FetchData.class);
            Bundle bundle = new Bundle();
            bundle.putString("auth", token);
            intent.putExtras(bundle);
            ConnectToEndomondo.this.startActivity(intent);
        }
        else {
            updateDisplay("Login Failed, returned value from Endomondo is: "+token);
        }

    }

    private String parseToken(String token) {

        int begin=token.indexOf("authToken=");
        int end= token.indexOf("measure=");
        String temp= token.substring(begin, --end);

        int start = temp.indexOf('=');

        String result=temp.substring(++start);
        return result;
    }

    private String serviceToken(String email, String password, String clientId, String action, String country) {

        call=RetroClient
                .getRetroInstant()
                .getApi()
                .getToken(email, password, clientId, action, country);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    token = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //updateDisplay(token);// remove after testing
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ConnectToEndomondo.this, "PayLoadFailed", Toast.LENGTH_SHORT).show();
            }
        });
        return token;
    }

    private void updateDisplay(String t) {
        ouput.append(t);
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

}
