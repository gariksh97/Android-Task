package com.android_project.kt.datrackchat.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.models.Game;
import com.android_project.kt.datrackchat.models.Word;

//TODO: Карточки
public class GameFragment extends Fragment implements View.OnClickListener {
    private static final int R_LAYOUT = R.layout.game_fragment_layout;

    public GameFragment() {
    }

    public static Fragment newInstance(MainActivity activity) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putParcelable("activity", activity);
        fragment.setArguments(args);
        return fragment;
    }

    Button answer1, answer2, answer3, answer4;
    Button[] answers = new Button[]{answer1, answer2, answer3, answer4};
    Button change, next;
    TextView problem;
    MainActivity activity;
    Boolean useNativeDictionary = true;
    Game game;

    private void pressAnswer(View v) {
        if (game.finish) return;
        game.finish = true;
        v.setBackgroundColor(Color.RED);
        if (game.rightAns == 0) answer1.setBackgroundColor(Color.GREEN);
        if (game.rightAns == 1) answer2.setBackgroundColor(Color.GREEN);
        if (game.rightAns == 2) answer3.setBackgroundColor(Color.GREEN);
        if (game.rightAns == 3) answer4.setBackgroundColor(Color.GREEN);
    }

    private void pressNext() {
        game = new Game();
        game.startGame(activity);
        answer1.setBackgroundColor(Color.WHITE);
        answer2.setBackgroundColor(Color.WHITE);
        answer3.setBackgroundColor(Color.WHITE);
        answer4.setBackgroundColor(Color.WHITE);
        answer1.setText(useNativeDictionary ? game.words[0].russianWord : game.words[0].nativeWord);
        answer2.setText(useNativeDictionary ? game.words[1].russianWord : game.words[1].nativeWord);
        answer3.setText(useNativeDictionary ? game.words[2].russianWord : game.words[2].nativeWord);
        answer4.setText(useNativeDictionary ? game.words[3].russianWord : game.words[3].nativeWord);
        problem.setText(useNativeDictionary ? game.words[game.rightAns].nativeWord
                : game.words[game.rightAns].russianWord);
    }

    private void pressChange() {
        useNativeDictionary = !useNativeDictionary;
        pressNext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getArguments().getParcelable("activity");
        View rootView = inflater.inflate(R_LAYOUT, container, false);
        answer1 = (Button) rootView.findViewById(R.id.ans1);
        answer2 = (Button) rootView.findViewById(R.id.ans2);
        answer3 = (Button) rootView.findViewById(R.id.ans3);
        answer4 = (Button) rootView.findViewById(R.id.ans4);
        change = (Button) rootView.findViewById(R.id.change_but);
        next = (Button) rootView.findViewById(R.id.next_but);
        problem = (TextView) rootView.findViewById(R.id.problem_label);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        change.setOnClickListener(this);
        next.setOnClickListener(this);
        pressNext();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ans1:
            case R.id.ans2:
            case R.id.ans3:
            case R.id.ans4:
                pressAnswer(v);
                break;
            case R.id.next_but:
                pressNext();
                break;
            case R.id.change_but:
                pressChange();
                break;
        }
    }
}