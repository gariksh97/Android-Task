package com.android_project.kt.datrackchat.firebase;

import android.net.Credentials;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.android_project.kt.datrackchat.addfriend.FriendItem;
import com.android_project.kt.datrackchat.chat.dialogs.DialogItem;
import com.android_project.kt.datrackchat.chat.messages.MessageItem;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by garik on 17.12.16.
 */

public class FirebaseRequests {

    public static String encode(String s) {
        return Uri.encode(
                Uri.encode(s).replaceAll("\\.", "%252")
        );
    }

    public static String decode(String s) {
        return Uri.decode(
                Uri.decode(s).replaceAll("%252", "\\.")
        );
    }

    public static void register(GoogleSignInAccount googleAccount, String uid) {
        String email = googleAccount.getEmail();

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();

        FriendItem item = new FriendItem();
        item.setEmail(encode(email));
        item.setName(encode(googleAccount.getDisplayName()));
        item.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference
                .child("users")
                .child(uid)
                .setValue(item);
    }

    public static DatabaseReference getCurrentUserDialogs() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference()
                .child("dialogs_list").child(user.getUid());
    }

    public static DatabaseReference getDialogs() {
        return FirebaseDatabase.getInstance().getReference().child("dialogs");
    }


    public static DatabaseReference getDialog(String dialogUid) {
        return FirebaseDatabase.getInstance().getReference()
                .child("dialogs").child(dialogUid);
    }

    public static DatabaseReference getDialogMessages(String dialogUid) {
        return getDialog(dialogUid).orderByChild("data").getRef();
    }

    public static String getDialogSize(String dialogUid) {
        return getDialogMessages(dialogUid).limitToLast(1).getRef().getKey();
    }

    public static void pushMessage(String dialogUid, MessageItem item) {
        getDialogMessages(dialogUid).push().setValue(item);
    }

    public static void addFriend(FriendItem friendItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference dialog = databaseReference.child("dialogs").push();
        dialog.child("users").child(user.getUid()).setValue(true);
        dialog.child("users").child(friendItem.getUid()).setValue(true);

        DialogItem dialogItem = new DialogItem();
        dialogItem.setUid(dialog.getKey());

        dialogItem.setName(friendItem.getName());
        databaseReference.child("dialogs_list").child(user.getUid()).push().setValue(dialogItem);

        dialogItem.setName(encode(user.getDisplayName()));
        databaseReference.child("dialogs_list").child(friendItem.getUid()).push().setValue(dialogItem);
    }


}
