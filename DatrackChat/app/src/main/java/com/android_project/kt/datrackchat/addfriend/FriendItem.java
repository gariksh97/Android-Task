package com.android_project.kt.datrackchat.addfriend;

import com.android_project.kt.datrackchat.firebase.FirebaseRequests;

/**
 * Created by garik on 18.12.16.
 */

public class FriendItem {
    String uid;
    String name;
    String email;

    public String getUid() {
        return uid;
    }

    public String decodeEmail() {
        return FirebaseRequests.decode(email);
    }

    public String decodeName() {
        return FirebaseRequests.decode(name);
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
