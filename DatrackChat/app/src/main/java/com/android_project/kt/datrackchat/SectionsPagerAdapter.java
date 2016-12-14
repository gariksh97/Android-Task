package com.android_project.kt.datrackchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android_project.kt.datrackchat.persons.PersonListFragment;
import com.android_project.kt.datrackchat.dictionary.DictionaryFragment;
import com.android_project.kt.datrackchat.game.GameFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return PersonListFragment.newInstance();
            case 1: return DictionaryFragment.newInstance();
            default: return GameFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chat";
            case 1:
                return "Dictionary";
            case 2:
                return "Game";
        }
        return null;
    }
}