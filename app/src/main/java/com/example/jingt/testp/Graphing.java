package com.example.jingt.testp;

import android.app.Activity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Graphing {

    public Activity activity; // Calling from Main Activity Class //
    //ArrayList<String> x_axis=new ArrayList<String>();

    ArrayList<Date> current_date_values = new ArrayList<Date>(); //
    ArrayList<Date> x_axis = new ArrayList<Date>();

    ArrayList<Double> x_axis_double = new ArrayList<Double>();
    ArrayList<Integer> y_axis=new ArrayList<Integer>();
    ArrayList<Double> y_axis_double=new ArrayList<Double>();
    GraphView graph; // To display graph //
    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(); // Actual Data Series to plot //
    DataPoint[] values; // To set the data points //

    String main_title;
    String x_title;
    String y_title;

    boolean isYDouble = false;
    boolean isYInteger = false;
    boolean isPair = false;

    // Default Constructor //
    public Graphing(Activity _activity){
        Log.d("test3","is this working");
        this.activity = _activity;
        Log.d("test4","is this working");
    }

    // USER chooses which graph to display //
    public void select_graph(int choice){
        // All graph one but different labels for x and y axis to set//
        //graph = (GraphView)this.activity.findViewById(R.id.graph1);
        switch(choice){

            case 1:
                graph = (GraphView)this.activity.findViewById(R.id.graph1);
                main_title = "Graph 1";
                x_title = "Start Time"; // String
                y_title = "Duration (Sec)"; // Integer
                isYInteger = true;
                break;

            case 2:
                graph = (GraphView)this.activity.findViewById(R.id.graph2);
                main_title = "Graph 2";
                x_title = "Start Time"; // String
                y_title = "Distance (KM)"; //Double
                isYDouble = true;
                break;

            case 3:
                main_title = "Graph 3";
                x_title = "Distance"; // Double
                y_title = "Average Speed"; // Double
                isPair = true;
                break;
        }

    }

    // List of Strings with date format //
    public void str_format_date(List<String> string_dates){
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> string_dates_edit = new ArrayList<String>();

        for(int i = 0; i < string_dates.size(); i++)
        {
            string_dates_edit.add(string_dates.get(i).substring(0,20)); // Parse the dates //
            Log.d("current date", string_dates_edit.get(i));
        }

        Date new_date = null;
        clearX();
        current_date_values.clear();
        for(int i = 0; i < string_dates_edit.size(); i++)
        {
            try{
                new_date = sdf.parse(string_dates_edit.get(i));
                Log.d("current date2", string_dates_edit.get(i));
                current_date_values.add(new_date);
                //x_axis.add(new_date);
            }
            catch (ParseException e) {
                Log.e("ERROR", "Here");
                e.printStackTrace();
            }
        }
        setX_axis(current_date_values);
    }


    // Need to be ascending order or it will crash //
    public void setX_axis(List<Date> x_data_list){
        for(int i = 0; i < x_data_list.size(); i++) {
            Date temp = (Date)x_data_list.get(i);
            x_axis.add(temp);
        }
    }

    public void setX_axis_Double(List<Double> x_axis_double) {
        this.x_axis_double = (ArrayList) x_axis_double;
    }

    public void setY_axis_Integer(List<Integer> y_data_list){
        clearY();
        for(int i = 0; i < y_data_list.size(); i++){
            //y_axis.add(String.valueOf(y_data_list.get(i)));
            y_axis.add(y_data_list.get(i));
        }

    }

    public void setY_axis_Double(List<Double> y_data_list){
        clearY();
        for(int i = 0; i < y_data_list.size(); i++){
            //y_axis.add(String.valueOf(y_data_list.get(i)));
            y_axis_double.add(y_data_list.get(i));
        }

    }

    public void clearX(){
        x_axis.clear();
        x_axis_double.clear();
    }

    public void clearY(){
        y_axis.clear();
        y_axis_double.clear();
    }


    public void set_series(){
        int n = x_axis.size();

        Log.d("Test1","Value " + n);
        values = new DataPoint[n];
        for(int i=0;i<n;i++){
            //DataPoint v = new DataPoint(Double.parseDouble(x_axis.get(i)),Double.parseDouble(y_axis.get(i)));
            DataPoint v;
            if (isYDouble == true)
            {
                v = new DataPoint((x_axis.get(i)),(y_axis_double.get(i)));
            }
            // Integer version //
            else{
                v = new DataPoint((x_axis.get(i)),(y_axis.get(i)));
            }
            values[i] = v;
            series.appendData(v,false,n,true);
        }


        // Completed setting the values for series //
        //Log.d("test", "Here" + n);
        Log.d("test2", "Value" + values);
        //series = new LineGraphSeries<DataPoint>(test_val);
        //series = new LineGraphSeries<DataPoint>(values);
        //setGraph(graph);

    }

    public void set_series_myPair(ArrayList<myPair> unsorted_list){
        int n = unsorted_list.size();

        values = new DataPoint[n];
        DataPoint v;
        for(int i = 0; i < n; i++)
        {
            v = new DataPoint(unsorted_list.get(i).getX(), unsorted_list.get(i).getY());
            values[i] = v;
            series.appendData(v,false,n,true);
        }
        Log.d("Pair Test", "Value" + values);
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public GraphView getGraph(){
        return graph;
    }

    public void setGraph1() {
        graph.addSeries(series);
        Log.d("test3", "HERE!");

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity.getApplicationContext()));
        Log.d("test4", "HERE!");
        //graph.getViewport().setMinX(x_axis.get(0).getTime());
        //graph.getViewport().setMaxX(x_axis.get(2).getTime());
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        setTitle(main_title, x_title, y_title);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(25000);
        Log.d("test5", "HERE!");


        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(x_axis.get(0).getTime());
        //graph.getViewport().setMaxX(x_axis.get(12).getTime());
        Log.d("test6", "HERE!");
        graph.getGridLabelRenderer().setHumanRounding(false);
        // graph.getViewport().setMinX(0);
        //graph.getViewport().setMaxX(20);

        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); //No zoom, just scroll - X's axis//
        graph.getViewport().setScrollableY(true); //No zoom, just scroll - Y's axis//
    }

    public void setGraph2(){

        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity.getApplicationContext()));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        setTitle(main_title, x_title, y_title);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(230);

        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(x_axis.get(0).getTime());
        //graph.getViewport().setMaxX(x_axis.get(12).getTime());

        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

    }



    public void setGraphPair(){
        graph.addSeries(series);
        setTitle(main_title, x_title, y_title);

        setTitle(main_title, x_title, y_title);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(20);


        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

    }



    public void setTitle(String main_title, String x_title, String y_title ){
        graph.setTitle(main_title);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle(x_title);
        gridLabel.setVerticalAxisTitle(y_title);
    }



}
