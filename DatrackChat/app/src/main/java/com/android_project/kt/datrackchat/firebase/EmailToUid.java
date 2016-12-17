package com.android_project.kt.datrackchat.firebase;

/**
 * Created by garik on 17.12.16.
 */

public class EmailToUid {
    String value;

    public EmailToUid(){};

    public EmailToUid(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
