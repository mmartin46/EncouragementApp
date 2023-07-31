package com.example.testapp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {

    private SimpleDateFormat mFormat;

    public MyXAxisValueFormatter() {
        mFormat = new SimpleDateFormat("MM-dd-yyyy HH:MM:SS");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date((long) value);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:MM:SS", Locale.ENGLISH);
        return sdf.format(date);
    }

    public int getDecimalDigits() {
        return 0;
    }
}
