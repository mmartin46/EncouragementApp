package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    private ArrayList<Pair<Date, Double>> lines;
    private ArrayList<Entry> dataValues;
    private ImageView backBtn;
    private Button shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mental_layout);

        backBtn = findViewById(R.id.backBtn);
        shareBtn = findViewById(R.id.shareButton);

        lineChart = (LineChart) findViewById(R.id.graph);
        lines = new ArrayList<>();

        try {
            initEntries();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        initLineChart();
        backBtnClicked(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sharedBtnClicked(this);
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

            SimpleDateFormat sDf = new SimpleDateFormat("MM-dd-yyyy");
            Date time = sDf.parse(temp[0]);

            // Add the date and value in the arraylist.
            Pair<Date, Double> p = new Pair<>(time, tempNum);
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
            dataValues.add(new Entry(lines.get(i).first.getTime(), lines.get(i).second.floatValue()));
        }
    }

    private void backBtnClicked(Context mContext) {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sharedBtnClicked(Context mContext) {
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String extStorageDir = Environment.getExternalStorageDirectory().toString();
                File file = new File(extStorageDir, "data.csv");

                Uri u = Uri.fromFile(file);


                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, u);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Send Mail"));
            }
        });
    }


    private void initLineChart() {
        XAxis xAxis = lineChart.getXAxis();
        MyXAxisValueFormatter mvF = new MyXAxisValueFormatter();
        xAxis.setValueFormatter(mvF);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(R.color.primaryColor);
        xAxis.setTextColor(R.color.primaryColor);

        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDrawMarkers(false);

        LineDataSet lineDataSet = new LineDataSet(dataValues, "Daily Mood");
        lineDataSet.setCircleColor(R.color.primaryColor);
        lineDataSet.setValueFormatter(mvF);
        lineDataSet.setColor(R.color.primaryColor);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        data.setValueTextColor(R.color.primaryColor);

        lineChart.setDescription(null);
        lineChart.setBorderColor(R.color.primaryColor);
        lineChart.setData(data);
        lineChart.invalidate();
    }

}