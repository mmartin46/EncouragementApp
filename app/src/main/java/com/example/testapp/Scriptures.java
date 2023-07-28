package com.example.testapp;

import java.util.HashMap;
import java.util.Random;

public class Scriptures {

    HashMap<String, String[]> scripture;
    Random rand = new Random();

    public Scriptures() {
        initScripture();
    }

    private void initScripture() {
        scripture = new HashMap<String, String[]>();
        String happy[] = {
                "James 5:13 \"Is any among you afflicted? let him pray. Is any merry? let him sing psalms.\"",
                "1 Thessalonians 5:16 \"Rejoice evermore.\"",
                "Psalm 37: \"Delight thyself also in the Lord: and he shall give thee the desires of thine heart.\""
        };
        String sad[] = {
                "John 14:1 \"Let not your heart be troubled: ye believe in God, believe also in me.\"",
                "Psalm 55:22 \" Cast thy burden upon the Lord, and he shall sustain thee: he shall never suffer the righteous to be moved.\"",
                "Psalm 37: \"Delight thyself also in the Lord: and he shall give thee the desires of thine heart.\""
        };

        scripture.put("HAPPY", happy);
        scripture.put("SAD", sad);
    }

    public String getScripture(String mood) {
        return scripture.get(mood)[rand.nextInt(scripture.get(mood).length)];
    }
}
