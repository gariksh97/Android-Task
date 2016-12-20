package com.android_project.kt.datrackchat.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Pair;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.models.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ivan on 19.12.16.
 */

public class DictionaryManager {
    private static TypedArray russianDictionary;
    private static TypedArray nativeDictionary;
    private static List<Word> dictionary;
    private static Random rand = new Random();

    private void getDictionaries(MainActivity activity) {
        if (russianDictionary != null) return;
        nativeDictionary = activity.getResources().obtainTypedArray(R.array.nativeDictionary);
        russianDictionary = activity.getResources().obtainTypedArray(R.array.russianDictionary);
        dictionary = new ArrayList<>();
        for (int i = 0; i < nativeDictionary.length(); ++i) {
            dictionary.add(new Word(nativeDictionary.getString(i), russianDictionary.getString(i)));
        }
        Log.d(LOG, "got dictionaries");
    }

    public TypedArray getRussianDictionary(MainActivity activity) {
        getDictionaries(activity);
        return russianDictionary;
    }

    public TypedArray getNativeDictionary(MainActivity activity) {
        getDictionaries(activity);
        return nativeDictionary;
    }

    public List<Word> getWholeDictionary(MainActivity activity) {
        getDictionaries(activity);
        return dictionary;
    }

    public List<Word> getSubDict(MainActivity activity, CharSequence chars) {
        if (dictionary == null) getDictionaries(activity);
        List<Word> subDict = new ArrayList<>();
        String searchString = chars.toString().toLowerCase();
        if (searchString.matches("^[a-z]+$")) {
            for (Word word : dictionary) {
                if (word.nativeWord.startsWith(searchString)) subDict.add(word);
            }
        } else if (searchString.matches("^[а-я]+$")) {
            String[] subWords;
            for (Word word : dictionary) {
                subWords = word.russianWord.split(" ");
                for (String subWord : subWords) {
                    if (subWord.startsWith(searchString)) {
                        subDict.add(word);
                        break;
                    }
                }
            }
        }
        return subDict;
    }

    public Word getWord(Integer ind, MainActivity activity) {
        getDictionaries(activity);
        return new Word(nativeDictionary.getString(ind), russianDictionary.getString(ind));
    }

    /*
     *  Note: use flag == true if you want to search into russian dictionary
     */
    public Word getWord(String str, MainActivity activity, Boolean flag) {
        getDictionaries(activity);
        String lowerStr = str.toLowerCase();
        for (Word word : dictionary) {
            if ((flag ? word.russianWord : word.nativeWord).equals(lowerStr)) return word;
        }
        return null;
    }

    public Boolean checkWord(String word, MainActivity activity) {
        getDictionaries(activity);
        String lowerWord = word.toLowerCase();
        for (int i = 0; i < nativeDictionary.length(); ++i) {
            if (lowerWord.equals(nativeDictionary.getString(i).toLowerCase())) return true;
        }
        return false;
    }

    public int wordsAmount(MainActivity activity) {
        getDictionaries(activity);
        return nativeDictionary.length();
    }

    public Word getRandomWord(MainActivity activity) {
        getDictionaries(activity);
        int ind = rand.nextInt(nativeDictionary.length());
        return new Word(nativeDictionary.getString(ind), russianDictionary.getString(ind));
    }

    final String LOG = "DictionaryManager";
}
