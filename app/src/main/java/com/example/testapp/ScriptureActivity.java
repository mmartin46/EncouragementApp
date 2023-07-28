package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScriptureActivity extends AppCompatActivity {

    private TextView cloudText;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scripture_layout);

        this.cloudText = findViewById(R.id.cloudText);
        this.backBtn = findViewById(R.id.backBtn);

        setCloudText();
        backBtnClicked();
    }

    public ScriptureActivity() {}


    public void setCloudText() {
        cloudText.setText(MainSingleton.getInstance().getChosenScripture());
    }

    private void backBtnClicked() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}