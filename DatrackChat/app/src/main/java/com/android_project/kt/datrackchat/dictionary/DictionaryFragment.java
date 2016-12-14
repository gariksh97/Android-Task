package com.android_project.kt.datrackchat.dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_project.kt.datrackchat.game.GameFragment;
import com.android_project.kt.datrackchat.R;

//TODO: Словарь
public class DictionaryFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.dictionary_fragment_layout;

    public DictionaryFragment() {}

    public static Fragment newInstance() {
        GameFragment fragment = new GameFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R_LAYOUT, container, false);
        return rootView;
    }
}