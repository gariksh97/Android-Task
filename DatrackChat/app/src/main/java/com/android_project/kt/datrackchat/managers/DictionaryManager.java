package com.android_project.kt.datrackchat.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.models.Word;

import java.util.List;

/**
 * Created by ivan on 19.12.16.
 */

public class DictionaryManager {
    private static List<String> russianDictionary;
    private static List<String> nativeDictionary;

    private void getDictionaries(MainActivity activity) {
        if (russianDictionary != null) return;
        SharedPreferences sPrefs = activity.getSharedPreferences("dictionary.xml", Context.MODE_PRIVATE);
        for (String str: sPrefs.getStringSet("russianDictionary", null)) {
            russianDictionary.add(str);
        }
        for (String str: sPrefs.getStringSet("nativeDictionary", null)) {
            nativeDictionary.add(str);
        }
    }

    public List<String> getRussianDictionary(MainActivity activity) {
        getDictionaries(activity);
        return russianDictionary;
    }

    public List<String> getNativeDictionary(MainActivity activity) {
        getDictionaries(activity);
        return nativeDictionary;
    }

    public Word getWord(Integer ind, MainActivity activity) {
        getDictionaries(activity);
        return new Word(nativeDictionary.get(ind), russianDictionary.get(ind));
    }

    /*
     *  Note: use flag == true if you want to search into russian
     */
    public Word getWord(String word, MainActivity activity, Boolean flag) {
        getDictionaries(activity);
        //Need to implement
        return null;
    }
    public Boolean checkWord(String word, MainActivity activity) {
        getDictionaries(activity);
        for (String str: nativeDictionary) {
            if (word.equals(str)) return true;
        }
        return false;
    }
}
