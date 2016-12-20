package com.android_project.kt.datrackchat;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by garik on 20.12.16.
 */

public class ArgumentsBundle implements Parcelable {
    HashMap<String, Object> map;

    public ArgumentsBundle() {
        map = new HashMap<>();
    }

    protected ArgumentsBundle(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArgumentsBundle> CREATOR = new Creator<ArgumentsBundle>() {
        @Override
        public ArgumentsBundle createFromParcel(Parcel in) {
            return new ArgumentsBundle(in);
        }

        @Override
        public ArgumentsBundle[] newArray(int size) {
            return new ArgumentsBundle[size];
        }
    };

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

}
