package com.android_project.kt.datrackchat.models;

import java.util.Random;

/**
 * Created by ivan on 19.12.16.
 */

public class Game {
    Random rand = new Random();
    public Integer rightAns = 2;
    public Boolean finish = false;
    public Word[] words = new Word[]{};

    public void startGame() {
        rightAns = rand.nextInt(4);
        finish = false;
        words = new Word[]{
                new Word("1", "2"),
                new Word("11", "22"),
                new Word("111", "222"),
                new Word("1111", "2222"),
        };
    }
}
