package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;

public class ScriptureActivity extends AppCompatActivity {

    private TextView cloudText;
    private Button copyButton, shareButton;
    private ImageView backBtn;
    private ClipboardManager clipboard;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scripture_layout);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        this.cloudText = findViewById(R.id.cloudText);
        this.backBtn = findViewById(R.id.backBtn);
        this.copyButton = findViewById(R.id.copyButton);
        this.shareButton = findViewById(R.id.shareButton);

        setCloudText();
        backBtnClicked();
        copyBtnClicked(this);
        shareBtnClicked(this);
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

    private void copyBtnClicked(Context mContext) {
        copyButton.setOnClickListener(v -> {
            ClipData clip = ClipData.newPlainText("label", cloudText.getText());
            clipboard.setPrimaryClip(clip);

            toast = Toast.makeText(mContext, "Copied text.", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void shareBtnClicked(Context mContext) {
        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            mContext.startActivity(Intent.createChooser(shareIntent, "Share via..."));
        });
    }
}