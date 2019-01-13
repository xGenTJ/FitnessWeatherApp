package com.example.jingt.testp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class WeatherGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_graph);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Weather Statistics Graph");

        cartesian.yAxis(0).title("Temperature");
        cartesian.yAxis(1).title("WindSpeed").orientation("right");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
      /*  seriesData.add(new CustomDataEntry("11.1", 8.0, 4.0, 10.0));
        seriesData.add(new CustomDataEntry("11.2", 5.1, 3.1, 22.75));
        seriesData.add(new CustomDataEntry("11.3", 4.9, 2.4, 22.75));
        seriesData.add(new CustomDataEntry("11.4", 6.7, 2.1, 15.0));
        seriesData.add(new CustomDataEntry("11.5", 6.1, 0.9, 16.25));
        seriesData.add(new CustomDataEntry("11.6", 10.0, 9.0, 20.25));*/

        for (int i = 0; i < ApiActivity.dataPoints.size(); i++)
        {
            seriesData.add(new CustomDataEntry(ApiActivity.dataPoints.get(i).getDate(), ApiActivity.dataPoints.get(i).getTemperature(),ApiActivity.dataPoints.get(i).getWindSpeed(), ApiActivity.dataPoints.get(i).getWindDirection()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Temperature");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("WindSpeed");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("WindDirection");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
