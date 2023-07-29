package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.os.Environment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MentalLayout extends AppCompatActivity {

    LineChart lineChart;
    private ArrayList<Pair<Long, Double>> lines;
    private ArrayList<Entry> dataValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mental_layout);

        lineChart = (LineChart) findViewById(R.id.graph);
        lines = new ArrayList<>();

        try {
            initEntries();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        initLineChart();
    }

    private void getDataSet() throws IOException, ParseException {
        // Find the file
        String extStorageDir = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDir, "data.csv");
        FileInputStream inStream = new FileInputStream(file);

        // Read the file contents
        BufferedReader bufReader = new BufferedReader(new BufferedReader(new InputStreamReader(inStream)));

        String line = bufReader.readLine();
        while (line != null) {

            // Split the date from the value
            String[] temp = line.split(",");
            double tempNum = Double.parseDouble(temp[1]);

            SimpleDateFormat sDf = new SimpleDateFormat("MM-dd-yyy HH:MM:SS");
            Date time = sDf.parse(temp[0]);

            // Add the date and value in the arraylist.
            Pair<Long, Double> p = new Pair<>(time.getTime(), tempNum);
            lines.add(p);
            // Go to the next line.
            line = bufReader.readLine();
        }
        bufReader.close();
    }


    private void initEntries() throws IOException, ParseException {
        getDataSet();

        dataValues = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++)
        {
            System.out.println(lines.get(i));
            dataValues.add(new Entry(lines.get(i).first, lines.get(i).second.floatValue()));
        }
    }

    private void initLineChart() {
        XAxis xAxis = lineChart.getXAxis();
        MyXAxisValueFormatter mvF = new MyXAxisValueFormatter();
        xAxis.setValueFormatter(mvF);

        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        LineDataSet lineDataSet = new LineDataSet(dataValues, "Dataset");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }

}