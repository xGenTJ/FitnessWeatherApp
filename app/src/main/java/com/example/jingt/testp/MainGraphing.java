package com.example.jingt.testp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class MainGraphing extends AppCompatActivity {



    static ArrayList<Integer> y_axis_int = new ArrayList<Integer>();
    static ArrayList<Double> y_axis = new ArrayList<Double>();

    static ArrayList<Date> x_axis = new ArrayList<Date>();
    static ArrayList<Double> x_axis_double = new ArrayList<Double>();

    String time1 = "2018-10-03T09:35:00.000Z";
    String time2 = "2018-10-03T23:00:00.000Z";
    String time3 = "2018-10-04T14:35:00.000Z";
    String time4 = "2018-10-05T17:35:00.000Z";
    String time5 = "2018-10-06T19:35:00.000Z";

    static ArrayList<String> time_list = new ArrayList<String>();
    static ArrayList<myPair> unsorted_list = new ArrayList<myPair>();

    //static Graphing new_graph;
    static Graphing new_graph_1;
    static Graphing new_graph_2;
    static TextView durationText;
    static TextView distanceText;

    boolean isPair = false; // For Graph #3 --> Needs the X to be in ASC order //
    boolean isGraph1 = false;
    boolean isGraph2 = false;

    ArrayList<WorkOut> jsonData ;
    static WorkOutSet current_set = new WorkOutSet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_graphing);

        Log.d("test1","is this working");

        jsonData = getIntent().getExtras().getParcelableArrayList("workoutData");

        //List<WorkOut> jsonData = getIntent().getExtras().getParcelable("workoutData");

        Log.d("parcelable", "please tell me this works");

        //new GraphingTask().execute("Starting Graphing Task");
        //new_graph = new Graphing(this);
        new_graph_1 = new Graphing(this);
        new_graph_2 = new Graphing(this);
        Log.d("test2","is this working");

        /*
            GRAPH #1 & GRAPH #2 are ONLY Functional if you call them one at a time from this onCreate() method.
            - The buttons do not work properly. It would take 2 - 3 minutes to render the x-axis and it will skip frames.
            - Async and creating a new thread didn't make it run better.
            - ** THE ISSUE MIGHT BE FROM THE CONVERSIONS DONE IN THE BACKGROUND **
            - Though, I attempted to use Async to run it in the background but it doesn't work...
            >> String --> UNIX time --> Java.Date --> getTime() [Results in: 2018/10/03]
         */

        // TESTING SCENARIO: GRAPH #1 //
        // CREATE A GRAPH via Constructor + Set Labels //


        /*new_graph.select_graph(1);

        // SETTING X-AXIS: String List via str_format_date() //
        time_list.add(time1);
        time_list.add(time2);
        time_list.add(time3);
        time_list.add(time4);
        time_list.add(time5);
        new_graph.str_format_date(time_list);

        // SETTING Y-AXIS: Integer List //
        y_axis_int.add(30);
        y_axis_int.add(20);
        y_axis_int.add(40);
        y_axis_int.add(10);
        y_axis_int.add(50);
        new_graph.setY_axis_Integer(y_axis_int);

        // DISPLAY GRAPH via set_series() //
        displaying_graph();
        time_list.clear();*/


        /* ==========================================================
            TO SET: Using Workout Data --> Get X values & Y Values
            - Extract from WorkOutSet the List<WorkOut>
            - Get the necessary parameters.
            >> UNCOMMENT OUT THESE LINES BELOW <<
           ==========================================================
         */

        //to test graph 1 uncomment this block


        new_graph_1.select_graph(1);
        //new_graph.select_graph(1);
        isGraph1 = true;
        WorkOutSet current_set = new WorkOutSet();
        //List<WorkOut> data = current_set.getworkOutList(); // GET list<Workout>

        for(int i = jsonData.size() - 1; i >= 0; i--)
        {
            WorkOut current_data = jsonData.get(i);
            Log.d("Test_index", "current " + i);
            Log.d("Test_value", "Value " + current_data.getStartTime());
            time_list.add(current_data.getStartTime());
            y_axis_int.add(current_data.getDurationSec());
        }

        new_graph_1.str_format_date(time_list);
        new_graph_1.setY_axis_Integer(y_axis_int);
        //new_graph.str_format_date(time_list);
        //new_graph.setY_axis_Integer(y_axis_int);
        displaying_graph();
        //time_list.clear();



        // TESTING SCENARIO: GRAPH #2 //

         /* ==========================================================
            TO SET: Using Workout Data --> Get X values & Y Values
            - Extract from WorkOutSet the List<WorkOut>
            - Get the necessary parameters.
            >> UNCOMMENT OUT THESE LINES BELOW <<
           ==========================================================
         */

        //to test graph2 uncomment this block

        new_graph_2.select_graph(2);
        //new_graph.select_graph(2);
        isGraph2 = true;
        current_set = new WorkOutSet();
        //List<WorkOut> data = current_set.getworkOutList(); // GET list<Workout>

        for(int i = jsonData.size() - 1; i >= 0; i--)
        {
            WorkOut current_data = jsonData.get(i);
            Log.d("Time_Value_2", time_list.get(i));
            //time_list.add(current_data.getStartTime()); // Using same Time List as before.
            y_axis.add(current_data.getDistanceKm());
            Log.d("Current_Distance", "Value " + current_data.getDistanceKm());
        }

        new_graph_2.str_format_date(time_list);
        new_graph_2.setY_axis_Double(y_axis);

        //new_graph.str_format_date(time_list);
        //new_graph.setY_axis_Double(y_axis);

        displaying_graph();
        time_list.clear();



        /* GRAPH #3 Runs with the Button */

        // TESTING SCENARIO: GRAPH #3 //
        // CREATE A GRAPH via Constructor + Set Labels //
        //new_graph = new Graphing(this);
        //new_graph.select_graph(3);

        durationText = (TextView) findViewById(R.id.durationInfo);
        distanceText = (TextView) findViewById(R.id.distanceInfo);
        durationText.setText(durationFilter(jsonData));
        distanceText.setText(distanceFilter(jsonData));

        Log.d("Text_On_Duration_Info ", durationFilter(jsonData));
        Log.d("Text_On_Distance_Info ", distanceFilter(jsonData));

        Log.d("test5","is this working");//  Tung added




    }

    /* Data Extreme Filter  and Graph Summary - Tung created */

    // Data and Graph Summary on Duration


    public String durationFilter(ArrayList<WorkOut> jsonData){
        //Intialize value
        double maxDuration =0;
        double minDuration = (double)jsonData.get(jsonData.size() - 1).getDurationSec();
        double avergageDuration = 0;
        double totalDuration = 0;
        int noOfWorkoutDay = jsonData.size();
        String dateOfMaxDuration = "";
        String dateOfMinDuration = "";


        for(int i = jsonData.size() - 1; i >= 0; i--)
        {
            WorkOut current_data = jsonData.get(i);
            //Finding max and min Distance
            totalDuration += Math.round((double)current_data.getDurationSec());
            if (maxDuration < (double)current_data.getDurationSec()){
                maxDuration = Math.round((double)current_data.getDurationSec());
                String s = current_data.getStartTime();
                dateOfMaxDuration = s.substring(0, Math.min(s.length(), 10));
            }

            if (minDuration>(double)current_data.getDurationSec()){
                minDuration = Math.round((double)current_data.getDurationSec());
                String s = current_data.getStartTime();
                dateOfMinDuration = s.substring(0, Math.min(s.length(), 10));
            }

        }
        if (noOfWorkoutDay == 0){
            avergageDuration = 0;
        }else{
            avergageDuration = Math.round(totalDuration/noOfWorkoutDay);
        }
        Log.d("Date_of_MaxDuration ", dateOfMaxDuration);
        Log.d("Max_Duration", "Value: " + maxDuration);
        Log.d("Date_of_MinDuration ", dateOfMinDuration);
        Log.d("Min_Duration", "Value: " + minDuration);
        Log.d("Average_Dutation", "Value: " + avergageDuration);

        String outputResult = "Max Duration: " + maxDuration + " on " + dateOfMaxDuration + "\n" +
                "Min Duration: " + minDuration + " on " + dateOfMinDuration + "\n" +
                "Total Workout Day: " + noOfWorkoutDay + "\n"  +
                "Total Duration: " + totalDuration + "\n" +
                "Average Duration: " + avergageDuration;

        return outputResult;
    }
    // Data and Graph on Distance

    public String distanceFilter( ArrayList<WorkOut> jsonData){
        //Intialize value
        double maxDistance =0;
        double minDistance = (double)jsonData.get(jsonData.size() - 1).getDistanceKm();
        double avergageDistance =0;
        double totalDistance = 0;
        int noOfWorkoutDay = jsonData.size();
        String dateOfMaxDistance = "";
        String dateOfMinDistance = "";

        for(int i = jsonData.size() - 1; i >= 0; i--)
        {
            WorkOut current_data = jsonData.get(i);

            //Finding max and min Distance
            totalDistance += Math.round((double)current_data.getDistanceKm()* 100.0) / 100.0;

            if (maxDistance < (double)current_data.getDistanceKm()){
                maxDistance = Math.round((double)current_data.getDistanceKm()* 100.0) / 100.0;
                String s = current_data.getStartTime();
                dateOfMaxDistance = s.substring(0, Math.min(s.length(), 10));
            }

            if (minDistance>(double)current_data.getDistanceKm()){
                minDistance =  Math.round((double)current_data.getDistanceKm()* 100.0) / 100.0;
                String s = current_data.getStartTime();
                dateOfMinDistance = s.substring(0, Math.min(s.length(), 10));
            }
        }

        if (noOfWorkoutDay == 0){
            avergageDistance = 0;
        }else{
            avergageDistance = Math.round((totalDistance/noOfWorkoutDay)* 100.0) / 100.0;
        }

        Log.d("Date_of_MaxDistance ", dateOfMaxDistance);
        Log.d("Max_Distance", "Value: " + maxDistance);
        Log.d("Date_of_MinDistance ", dateOfMinDistance);
        Log.d("Min_Distance", "Value: " + minDistance);
        Log.d("Average_Distance", "Value: " + avergageDistance);

        String outputResult = "Max Distance: " + maxDistance + " on " + dateOfMaxDistance + "\n" +
                "Min Distance: " + minDistance + " on " + dateOfMinDistance + "\n" +
                "Total Workout Day: " + noOfWorkoutDay + "\n"  +
                "Total Distance: " + totalDistance  + "\n" +
                "Average Distance: " + avergageDistance;

        return outputResult;
    }

    static private class GraphingTask extends AsyncTask<String, Graphing, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d("test1", "Value1");
            //time_list.add(time1);
            //time_list.add(time2);
            //time_list.add(time3);
            //time_list.add(time4);
            //time_list.add(time5);
            //y_axis_int.add(30);
            //y_axis_int.add(20);
            //y_axis_int.add(40);
            //y_axis_int.add(10);
            //y_axis_int.add(50);
            //new_graph.str_format_date(time_list);
            //new_graph.setY_axis_Integer(y_axis_int);
            new_graph_1.set_series();
            //publishProgress(new_graph);
            //new_graph.setGraph();
            Log.d("test2", "Value2");
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(Graphing... values) {
            //new_graph.setGraph();
        }

        protected void onPostExecute(String display_graph) {
            //new_graph.set_series();
            //new_graph.setGraph();
        }


    }


    /*public void select_button(View v) {

        switch (v.getId()) {

            case R.id.graph1_radio:
                time_list.clear();
                y_axis_int.clear();

                //displaying_graph();
                time_list.clear();
                break;

            case R.id.graph2_radio:
                time_list.clear();
                y_axis.clear();
                time_list.clear();
                break;


                //comment this block and uncomment the next one
            case R.id.graph3_radio:

                 /* ==========================================================
                 TO SET: Using Workout Data --> Get X values & Y Values
                 - Extract from WorkOutSet the List<WorkOut>
                 - Get the necessary parameters.
                 >> UNCOMMENT OUT THESE LINES BELOW <<
                ==========================================================
                */
/*
                x_axis_double.clear();
                y_axis.clear();
                isPair = true;
                new_graph.select_graph(3);
                current_set = new WorkOutSet();
                //jsonData = getIntent().getExtras().getParcelable("workoutData");

                ArrayList<WorkOut> data = jsonData; // GET list<Workout>
                for (int i = 0; i < data.size(); i++) {
                    WorkOut current_data = data.get(i);
                    x_axis_double.add(current_data.getDistanceKm());
                    y_axis.add(current_data.getSpeedKmhAvg());
                }
                for (int i = 0; i < x_axis_double.size(); i++) {
                    myPair new_pair;
                    new_pair = new myPair(x_axis_double.get(i), y_axis.get(i));
                    unsorted_list.add(new_pair);
                    Log.d("Test3", "Value" + unsorted_list.get(i).getX());
                }
                Comparator<myPair> comparator = new Comparator<myPair>() {
                    @Override
                    public int compare(myPair myPair, myPair t1) {
                        return myPair.getX().compareTo(t1.getX());
                    }
                };
                Collections.sort(unsorted_list, comparator);
                for (int i = 0; i < x_axis_double.size(); i++) {
                    Log.d("Test4.1", "Value" + unsorted_list.get(i).getX());
                    Log.d("Test4.2", "Value" + unsorted_list.get(i).getY());
                }
                new_graph.set_series_myPair(unsorted_list);
                displaying_graph();

                break;
        }

    }*/

    private void displaying_graph() {
        new Thread() {

            public void run() {
                //new_graph.set_series();
                //new_graph.setGraph();

                try {
                    if (isPair) {
                        new_graph_1.setGraphPair();
                        isPair = false;
                    } else if (isGraph1) {
                        new_graph_1.set_series();
                        new_graph_1.setGraph1();
                        isGraph1 = false;
                    }
                    else if (isGraph2){
                        new_graph_2.set_series();
                        new_graph_2.setGraph2();
                        isGraph2 = false;
                    }

                } catch (final Exception ex) {
                    Log.i("---", "Exception in thread");
                }

            }

        }.start();
    }

}