package com.android_project.kt.datrackchat.dictionary;

/**
 * Created by garik on 15.12.16.
 */

public interface DatrackTraslator {
    /**
     * @param word - word in selected language
     * @return word in datrack or null, if translate don't exists;
     */
    String translateToDatrack(String word);

    /**
     * @param word - word in datrack language
     * @return word in selected language or null, if word is incorrect;
     */
    String translateFromDatrack(String word);
}
