package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button searchBtn, clearBtn;
    Toast toast;

    EditText userInputText;
    EmotionRecognizer er;

    Scriptures scrip;

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

    private void searchBtnClicked() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInput();
            }
        });
    }



    private void clearBtnClicked() {
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
            }
        });
    }

    // Clears the input from the user's input.
    private void clearInput() {
        if (userInputText.getText().length() == 0) {
            toast = Toast.makeText(this, "Request already cleared.", Toast.LENGTH_SHORT);
            toast.show();
        }
        userInputText.getText().clear();
    }

    // Checks if the input is empty, else processes it.
    private void processInput() {
        String userInput = userInputText.getText().toString();
        userInput = userInput.toLowerCase();

        if (userInput.length() == 0) {
            toast = Toast.makeText(this, "Please type in your request.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            er = new EmotionRecognizer();
            int idx;
            for (idx = 0; idx < er.getHappyTokens().length; ++idx) {
                if (userInput.contains(er.happyTokens[idx])) {
                    er.incEmotion(0);
                }
            }

            for (idx = 0; idx < er.getSadTokens().length; ++idx) {
                if (userInput.contains(er.sadTokens[idx])) {
                    er.incEmotion(1);
                }
            }

            if (er.getEmotion(0) >= er.getEmotion(1))
            {
                System.out.println(scrip.getScripture("HAPPY"));
            }
            else
            {
                System.out.println(scrip.getScripture("SAD"));
            }
        }
    }


    private class EmotionRecognizer {


        // Emotion Recognition
        private String happyTokens[];
        private String sadTokens[];

        /*
        happy - 0
        sad - 1
         */
        private int emotionArr[];


        public EmotionRecognizer() {
            initTokens();
        }

        public void initTokens() {
            happyTokens = new String[] {
                    "happy", "glad", "joy"
            };

            sadTokens = new String[] {
                    "sad", "depressed"
            };
            emotionArr = new int[]{ 0, 0 };
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