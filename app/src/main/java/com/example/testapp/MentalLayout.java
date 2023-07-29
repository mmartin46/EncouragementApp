package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MentalLayout extends AppCompatActivity {

    LineChart lineChart;
    private ArrayList<String> lines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mental_layout);

        lineChart = (LineChart) findViewById(R.id.graph);

        try {
            getDataSet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getDataSet() throws IOException {
        String extStorageDir = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDir, "data.csv");

        FileInputStream inStream = new FileInputStream(file);

        BufferedReader bufReader = new BufferedReader(new BufferedReader(new InputStreamReader(inStream)));
        ArrayList<String> fileContents = new ArrayList<>();

        String line = bufReader.readLine();
        while (line != null) {
            fileContents.add(line);
//            System.out.println(line);
            line = bufReader.readLine();
        }
        bufReader.close();
    }

    private void initLineChart() {
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
    }

}