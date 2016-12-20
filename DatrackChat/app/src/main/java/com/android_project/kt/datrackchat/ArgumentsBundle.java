package com.android_project.kt.datrackchat;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by garik on 20.12.16.
 */

public class ArgumentsBundle implements Serializable {
    HashMap<String, Object> map;

    public ArgumentsBundle() {
        map = new HashMap<>();
    }

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
