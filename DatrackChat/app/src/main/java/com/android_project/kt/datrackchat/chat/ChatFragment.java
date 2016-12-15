package com.android_project.kt.datrackchat.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.SectionsPagerAdapter;
import com.android_project.kt.datrackchat.chat.persons.PersonListFragment;

public class ChatFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.chat_fragment_layout;

    public ChatFragment() {
    }

    public static Fragment newInstance(SectionsPagerAdapter adapter) {
        ChatFragment fragment = new ChatFragment();

        Bundle args = new Bundle();
        args.putParcelable("adapter", adapter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R_LAYOUT, container, false);

        final SectionsPagerAdapter adapter = getArguments().getParcelable("adapter");
        final PersonListFragment fragment = (PersonListFragment) PersonListFragment.newInstance("I'm winner");

        rootView.findViewById(R.id.button2).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.changeFragment(getChildFragmentManager(), 0, fragment);
                    }
                }
        );


        return rootView;
    }
}