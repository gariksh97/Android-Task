package com.android_project.kt.datrackchat.models;

import android.support.annotation.NonNull;

/**
 * Created by danilskarupin on 19.12.16.
 */

public class Word {

    @NonNull
    public final String nativeWord;

    @NonNull
    public final String russianWord;

    public Word(@NonNull String nativeWord,
                @NonNull String russianWord) {
        this.nativeWord = nativeWord;
        this.russianWord = russianWord;
    }
}
