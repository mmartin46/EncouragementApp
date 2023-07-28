package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScriptureActivity extends AppCompatActivity {

    private static TextView cloudText;
    private final ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scripture_layout);

        backBtnClicked();
    }

    public ScriptureActivity() {
        this.cloudText = findViewById(R.id.cloudText);
        this.backBtn = findViewById(R.id.backBtn);
    }

    public static void setCloudText(String scripture) {
        cloudText.setText((CharSequence) scripture);
    }

    private void backBtnClicked() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}