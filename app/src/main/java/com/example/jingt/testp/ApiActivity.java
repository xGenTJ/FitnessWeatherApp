package com.example.jingt.testp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.DataBlock;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Unit;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ApiActivity extends AppCompatActivity {

    //Buttons
    private Button button3;
    private Button segmentDifference;

    //Currently selected date
    private View currentSelection;

    //Text fields for dates
    private EditText startDate;
    private EditText endDate;

    //Montreal latitude/longitude
    double latitude = 45.50884;
    double longitude = -73.58781;
    public static ArrayList<TemperatureNode> dataPoints;

    //Data points array list
    //public static ArrayList<TemperatureNode> dataPoints = new ArrayList<TemperatureNode> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        //Button to get to graphs
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraph();
            }
        });



        // Forecast prediction section
        ForecastConfiguration configuration =
                new ForecastConfiguration.Builder("68250ea34ac21a1c28073da892a18660")
                        .setDefaultUnit(Unit.CA)
                        .setCacheDirectory(getCacheDir())
                        .build();
        ForecastClient.create(configuration);

    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    public void measureDifference(View v){
        startDate = (EditText)findViewById(R.id.startDateText);
        endDate = (EditText)findViewById(R.id.endDateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date start = sdf.parse(startDate.getText().toString(),new ParsePosition(0));
        Date end = sdf.parse(endDate.getText().toString(),new ParsePosition(0));

        Calendar sCal = Calendar.getInstance();
        Calendar eCal = Calendar.getInstance();

        sCal.setTime(start);
        eCal.setTime(end);

        long diff = eCal.getTimeInMillis() - sCal.getTimeInMillis();
        if(diff < 0){
            Toast.makeText(getApplicationContext(), "The starting date must come before the end date",Toast.LENGTH_LONG).show();
            return;
        }

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        if(dayCount > 20 | dayCount < 10){
            Toast.makeText(getApplicationContext(), "You must have between 10-20 days",Toast.LENGTH_LONG).show();
            return;
        }
        int dayCountInt = (int)dayCount;
        Log.v("Number of days: ", (int) Math.abs(dayCount)+ " Days");

        apiCall(dayCountInt, sCal, eCal);

    }
///
    private ArrayList<TemperatureNode> apiCall(int length, Calendar start, Calendar end){

        final ArrayList<TemperatureNode> dataPoints = new ArrayList<TemperatureNode>();
        final Calendar currentDay = start;
        final int size = length;
        for(int i = 0; i < length ; i++){

            Log.v("Date",(int)currentDay.getTimeInMillis()/1000 + " seconds");

            //Temp data
            final TemperatureNode tempData = new TemperatureNode(currentDay.getTimeInMillis()/1000);

            tempData.setDate(currentDay.getTime().toString());

            //Api call
            ForecastClient.getInstance()
                    .getForecast(latitude, longitude, (int)tempData.getDateSeconds(), new Callback<Forecast>() {
                        @Override
                        public void onResponse(Call<Forecast> forecastCall, Response<Forecast> response) {
                            if (response.isSuccessful()) {
                                Forecast forecast = response.body();
                                //Log.v("Date",currentDay.getTime().toString());
                                DataBlock data = forecast.getDaily();
                                Gson gson = new Gson();
                                String json = gson.toJson(data);
                                JSONObject obj;
                                JSONArray jsonData;
                                try {
                                    obj = new JSONObject(json);
                                    jsonData = obj.getJSONArray("data");
                                    Log.v("test",jsonData.getJSONObject(0).getString("apparentTemperatureHigh"));
                                    tempData.setTemperature(Double.parseDouble(jsonData.getJSONObject(0).getString("apparentTemperatureHigh")));
                                    tempData.setWindSpeed(Double.parseDouble(jsonData.getJSONObject(0).getString("windSpeed")));
                                    tempData.setWindDirection(Double.parseDouble(jsonData.getJSONObject(0).getString("windBearing")));
                                    dataPoints.add(tempData);

                                    Log.v("size", " "+dataPoints.size());
                                    if(dataPoints.size() == size){
                                       for( int i = 0; i < size; i++) {
                                           Log.v("temp0","Date: " + dataPoints.get(i).getDate()
                                                   + "Temperature: " + dataPoints.get(i).getTemperature()
                                                   + "WindSpeed: " + dataPoints.get(i).getWindSpeed()
                                                   + "windBearing: " + dataPoints.get(i).getWindDirection());
                                       }
                                       setDataPoints(dataPoints);
                                    }

                                    //dataPoints[i] = tempData;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.v("Data",json);
                            }
                            Log.v("tempdata",tempData.getTemperature()+"");
                        }
                        @Override
                        public void onFailure(Call<Forecast> forecastCall, Throwable t) {

                        }
                    });
            currentDay.add(Calendar.DATE, 1);

        }

        Collections.sort(dataPoints,comparator);
        return dataPoints;
    }
    Comparator<TemperatureNode> comparator = new Comparator<TemperatureNode>(){
        public int compare(TemperatureNode s1, TemperatureNode s2) {

            if(!s1.date.equals(s2.date)){
                return s2.date.compareTo(s1.date);
            }
            else{
                    return s1.date.compareTo(s2.date);
                }
            }

    };

    //Methods

    public void showDatePickerDialog(View v) {
        switch ( v.getId()){
            case R.id.buttonStart:
                setCurrentSelection(findViewById(R.id.startDateText));
                break;
            case R.id.buttonEnd:
                setCurrentSelection(findViewById(R.id.endDateText));
                break;
        }
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void openGraph(){
        Intent intent = new Intent(this,WeatherGraph.class);
        startActivity(intent);
        for( int i = 0; i < dataPoints.size(); i++) {
            Log.v("temp1"," Date: " + dataPoints.get(i).getDate()
                    + " Temperature: " + dataPoints.get(i).getTemperature()
                    + " WindSpeed: " + dataPoints.get(i).getWindSpeed()
                    + " windBearing: " + dataPoints.get(i).getWindDirection());
        }
    }

    //Getters and setters

    public View getCurrentSelection() {
        return currentSelection;
    }

    private void setCurrentSelection(View currentSelection) {
        this.currentSelection = currentSelection;
    }

    public ArrayList<TemperatureNode> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(ArrayList<TemperatureNode> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
