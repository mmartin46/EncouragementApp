package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    // Variables
    private Button searchBtn, clearBtn;

    /*
     The scripture that will be displayed on
     in the ScriptureActivity class.
     */
    private static String chosenScripture;


    private Toast toast;

    private EditText userInputText;
    private EmotionRecognizer er;

    private Scriptures scrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        clearBtn = findViewById(R.id.clearBtn);
        userInputText = findViewById(R.id.userInput);

        scrip = new Scriptures();
        searchBtnClicked();
        clearBtnClicked();
    }

    public void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
                return;
            }
        }
    }

    private void searchBtnClicked() {
        searchBtn.setOnClickListener(v -> {
            askPermission();
            try {
                processInput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (getUserInputText().length() > 0) {
                Intent intent = new Intent(getApplicationContext(), ScriptureActivity.class);
                startActivity(intent);
            }
        });
    }

    public EditText getUserInputText() {
        return userInputText;
    }

    public String getChosenScripture() {
        return chosenScripture;
    }

    public void setChosenScripture(String chosenScripture) {
        MainActivity.chosenScripture = chosenScripture;
    }


    private void clearBtnClicked() {
        clearBtn.setOnClickListener(v -> clearInput());
    }

    // Clears the input from the user's input.
    private void clearInput() {
        if (userInputText.getText().length() == 0) {
            toast = Toast.makeText(this, "Request already cleared.", Toast.LENGTH_SHORT);
            toast.show();
        }
        getUserInputText().getText().clear();
    }

    // Checks if the input is empty, else processes it.
    private void processInput() throws IOException {
        String userInput = getUserInputText().getText().toString();
        userInput = userInput.toLowerCase();

        // If the user used no input, send an alert to the user.
        if (userInput.length() == 0) {
            toast = Toast.makeText(this, "Please type in your request.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            er = new EmotionRecognizer();
            int idx;
            // Happy responses
            for (idx = 0; idx < er.getHappyTokens().length; ++idx) {
                if (userInput.contains(er.happyTokens[idx])) {
                    er.incEmotion(0);
                }
            }

            // Sad Responses
            for (idx = 0; idx < er.getSadTokens().length; ++idx) {
                if (userInput.contains(er.sadTokens[idx])) {
                    er.incEmotion(1);
                }
            }

            // If the response was happy.
            if (er.getEmotion(0) >= er.getEmotion(1))
            {
                setChosenScripture(scrip.getScripture("HAPPY"));
            }
            else
            {
                // If the response was sad.
                setChosenScripture(scrip.getScripture("SAD"));
            }
            writeToFile();
        }
        er.setEmotion(0, 0);
        er.setEmotion(1, 0);
    }

    private void writeToFile() throws IOException {
        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        LocalDate now = null;

        String extStorageDir = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDir, "data.csv");


        if (file.exists()) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                now = LocalDate.now();
            }

            write((date + "," + ((double) er.getEmotion(0) / er.getEmotion(1)) + "\n").getBytes(), file, true);
        }
        else
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                now = LocalDate.now();
            }

            write((date + "," + ((double) er.getEmotion(0) / er.getEmotion(1)) + "\n").getBytes(), file, false);
        }
    }

    private void write(byte[] data, File file, Boolean append) throws IOException {
        BufferedOutputStream bOS = null;

        try {
            FileOutputStream fOS = new FileOutputStream(file, append);
            bOS = new BufferedOutputStream(fOS);
            bOS.write(data);
        }
        finally
        {
            if (bOS != null)
            {
                try
                {
                    bOS.flush();
                    bOS.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // The class is used to recognize a user's emotion
    // based on keywords.
    private static class EmotionRecognizer {


        // Emotion Recognition
        private String[] happyTokens;
        private String[] sadTokens;

        /*
        happy - 0
        sad - 1
         */
        private int[] emotionArr;


        public EmotionRecognizer() {
            initTokens();
        }

        public void initTokens() {
            happyTokens = new String[] {
                    "happy", "glad", "joy",
                    "cheer", "elated", "well",
                    "bright", "fair", "hope"
            };

            sadTokens = new String[] {
                    "sad", "depressed", "miser",
                    "un", "bad", "upset", "broken",
                    "dis", "mourn", "low", "suicidal",
                    "grieve"
            };
            emotionArr = new int[]{ 1, 1 };
        }


        public String[] getHappyTokens() {
            return happyTokens;
        }


        public String[] getSadTokens() {
            return sadTokens;
        }


        public int[] getEmotionArr() {
            return emotionArr;
        }

        public void setEmotion(int idx, int value) {
            this.emotionArr[idx] = value;
        }

        public void incEmotion(int idx) {
            this.emotionArr[idx]++;
        }

        public void decEmotion(int idx) {
            this.emotionArr[idx]--;
        }

        public int getEmotion(int idx) {
            return emotionArr[idx];
        }
    }
}