package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
//import com.jjoe64.graphview.GraphView;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.LineGraphSeries;

import android.os.Bundle;

public class MentalLayout extends AppCompatActivity {

//    GraphView graphView;
//    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mental_layout);

//        graphView = findViewById(R.id.graph);
    }

//    private void createView() {
//        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 3),
//                new DataPoint(2, 4),
//                new DataPoint(3, 9),
//                new DataPoint(4, 6),
//                new DataPoint(5, 3),
//                new DataPoint(6, 6),
//                new DataPoint(7, 1),
//                new DataPoint(8, 2)
//        });
//
//        graphView.setTitle("Mental Tracker");
//        graphView.setTitleColor(R.color.primaryColor);
//        graphView.setTitleTextSize(20);
//        graphView.addSeries(series);
//
//    }


}