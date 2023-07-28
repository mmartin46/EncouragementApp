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
        scripture = new HashMap<>();
        String[] happy = {
                "James 5:13 \"Is any among you afflicted? let him pray. Is any merry? let him sing psalms.\"",
                "1 Thessalonians 5:16 \"Rejoice evermore.\"",
                "Psalm 37: \"Delight thyself also in the Lord: and he shall give thee the desires of thine heart.\"",
                "Proverbs 14:21 \"He that despiseth his neighbour sinneth: but he that hath mercy on the poor, happy is he.\""
        };
        String[] sad = {
                "John 14:1 \"Let not your heart be troubled: ye believe in God, believe also in me.\"",
                "Psalm 55:22 \" Cast thy burden upon the Lord, and he shall sustain thee: he shall never suffer the righteous to be moved.\"",
                "Psalm 37:4 \"Delight thyself also in the Lord: and he shall give thee the desires of thine heart.\"",
                "Proverbs 15:13 \"A merry heart maketh a cheerful countenance: but by sorrow of the heart the spirit is broken.\""

        };

        scripture.put("HAPPY", happy);
        scripture.put("SAD", sad);
    }

    public String getScripture(String mood) {
        return scripture.get(mood)[rand.nextInt(scripture.get(mood).length)];
    }
}
