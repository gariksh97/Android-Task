package com.android_project.kt.datrackchat.firebase;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by garik on 17.12.16.
 */

public class FirebaseRequests {

    public static String getUidByEmail(String email, String uid) {
        return null;
    }

    public static void pushUidByEmail(String email, String uid) {
        email = Uri.encode(email).replaceAll("\\.", "%252E");

        FirebaseDatabase.getInstance().getReference()
                .child("emails_to_uid").child(email).setValue(
                new EmailToUid(uid)
        );
    }
}
