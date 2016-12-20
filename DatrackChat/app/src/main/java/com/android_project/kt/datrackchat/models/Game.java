package com.android_project.kt.datrackchat.models;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.managers.DictionaryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ivan on 19.12.16.
 */

public class Game {
    Random rand = new Random();
    public Integer rightAns = 2;
    public Boolean finish = false;
    public Word[] words = new Word[]{};
    public void startGame(MainActivity activity) {
        rightAns = rand.nextInt(4);
        finish = false;
        rand.nextInt();
        DictionaryManager dm = new DictionaryManager();
        words = new Word[]{
                dm.getRandomWord(activity),
                dm.getRandomWord(activity),
                dm.getRandomWord(activity),
                dm.getRandomWord(activity)
        };
    }
}
