package com.android_project.kt.datrackchat.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.models.Word;

//TODO: Карточки
public class GameFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.game_fragment_layout;

    public GameFragment() {}

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
        MainActivity activity = getArguments().getParcelable("activity");
        SharedPreferences sPrefs = activity.getSharedPreferences("dictionary.xml", Context.MODE_PRIVATE);
        return rootView;
    }
}