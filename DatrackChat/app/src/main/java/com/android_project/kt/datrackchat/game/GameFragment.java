package com.android_project.kt.datrackchat.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_project.kt.datrackchat.R;

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
        return rootView;
    }
}