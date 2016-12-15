package com.android_project.kt.datrackchat;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.android_project.kt.datrackchat.chat.ChatFragment;
import com.android_project.kt.datrackchat.chat.persons.PersonListFragment;
import com.android_project.kt.datrackchat.dictionary.DictionaryFragment;
import com.android_project.kt.datrackchat.game.GameFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter
        //Small hack for Bundle
        implements Parcelable{

    private FragmentManager fm;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<>();
        for (int i = 0, l = getCount(); i < l; i++)
            fragments.add(new LinkedList<Fragment>());
    }

    public List<LinkedList<Fragment>> fragments;


    private SectionsPagerAdapter(Parcel in) {
        super(null);
    }

    public static final Creator<SectionsPagerAdapter> CREATOR = new Creator<SectionsPagerAdapter>() {
        @Override
        public SectionsPagerAdapter createFromParcel(Parcel in) {
            return new SectionsPagerAdapter(in);
        }

        @Override
        public SectionsPagerAdapter[] newArray(int size) {
            return new SectionsPagerAdapter[size];
        }
    };

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragments.get(0).size() == 0) {
                    fragments.get(0).add(ChatFragment.newInstance(this));
                }
                return fragments.get(0).getLast();
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

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof ChatFragment && fragments.get(0).getLast() instanceof PersonListFragment)
            return POSITION_NONE;
        return POSITION_UNCHANGED;
    }

    public void changeFragment(FragmentManager fm, int pos, Fragment newFragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragments.get(pos).getLast());
        //  transaction.add(newFragment, "Test");
        fragments.get(pos).add(newFragment);
        transaction.commitNow();
        fm.executePendingTransactions();
        notifyDataSetChanged();
    }

    public void backFragment(FragmentManager fm, int pos) {
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.remove(fragments.get(pos).getLast());
        fragments.get(pos).removeLast();
        transaction.add(fragments.get(pos).getLast(), null);
        transaction.commit();
        notifyDataSetChanged();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}