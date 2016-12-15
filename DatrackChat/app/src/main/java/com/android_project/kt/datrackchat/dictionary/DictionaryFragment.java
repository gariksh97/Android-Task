package com.android_project.kt.datrackchat.dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_project.kt.datrackchat.game.GameFragment;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.chat.persons.PersonListFragment;

//TODO: Словарь
public class DictionaryFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.dictionary_fragment_layout;

    public DictionaryFragment() {}

    public static Fragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R_LAYOUT, container, false);
        /*rootView.findViewById(R.id.dict_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        Fragment fragment = new GameFragment();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.dict_layout, fragment);
                        transaction.commit();
                        getChildFragmentManager().executePendingTransactions();
                    }
                }
        );*/
        return rootView;
    }
}