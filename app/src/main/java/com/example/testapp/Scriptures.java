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
                "James 5:13 \"Is any among you afflicted? let him pray. Is any merry? let him sing psalms.\"",
                "1 Thessalonians 5:16 \"Rejoice evermore.\"",
                "Psalm 37: \"Delight thyself also in the Lord: and he shall give thee the desires of thine heart.\""
        };

        scripture.put("HAPPY", happy);
        scripture.put("SAD", sad);
    }

    public String getScripture(String mood) {
        return scripture.get(mood)[rand.nextInt(scripture.get(mood).length)];
    }
}
