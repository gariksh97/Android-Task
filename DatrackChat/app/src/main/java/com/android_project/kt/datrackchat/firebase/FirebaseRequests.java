package com.android_project.kt.datrackchat.firebase;

import android.net.Uri;
import android.util.Log;

import com.android_project.kt.datrackchat.addfriend.FriendItem;
import com.android_project.kt.datrackchat.chat.dialogs.DialogItem;
import com.android_project.kt.datrackchat.chat.messages.MessageItem;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        if (Uri.decode(s) == null) return s;
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
                .child("dialogs_list").child(user.getUid()).orderByChild("data").getRef();
    }

    public static DatabaseReference getDialogs() {
        return FirebaseDatabase.getInstance().getReference().child("dialogs");
    }


    public static DatabaseReference getDialog(String dialogUid) {
        return FirebaseDatabase.getInstance().getReference()
                .child("dialogs")
                .child(dialogUid)
                .child("messages");
    }

    public static DatabaseReference getDialogMessages(String dialogUid) {
        return getDialog(dialogUid).child("messages")
                .orderByChild("data").getRef();
    }

    public static String getDialogSize(String dialogUid) {
        return getDialogMessages(dialogUid).limitToLast(1).getRef().getKey();
    }

    public static void pushMessage(DialogItem dialogItem, MessageItem item) {
        getDialog(dialogItem.getDialog_uid()).push().setValue(item);
        Log.d("MyLog", dialogItem.getDialog_uid());
        dialogItem.setData(item.getData());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("dialogs_list");
        pushDialogItem(reference, dialogItem.getFirst_user_uid(), dialogItem);
        pushDialogItem(reference, dialogItem.getSecond_user_uid(), dialogItem);
    }

    private static void pushDialogItem(DatabaseReference reference, String user_uid, DialogItem dialogItem) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user_uid.equals(user.getUid())) {
            reference.child(user_uid)
                    .child(dialogItem.getDialog_uid())
                    .setValue(dialogItem);
        } else  {
            String name = dialogItem.getName();
            dialogItem.setName(user.getDisplayName());
            reference.child(user_uid)
                    .child(dialogItem.getDialog_uid())
                    .setValue(dialogItem);
            dialogItem.setName(name);
        }
    }

    public static void addFriend(FriendItem friendItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference dialog = databaseReference.child("dialogs").push();
        dialog.child("users").child(user.getUid()).setValue(true);
        dialog.child("users").child(friendItem.getUid()).setValue(true);

        DialogItem dialogItem = new DialogItem();
        dialogItem.setDialog_uid(dialog.getKey());
        dialogItem.setFirst_user_uid(user.getUid());
        dialogItem.setSecond_user_uid(friendItem.getUid());

        dialogItem.setName(friendItem.getName());
        databaseReference.child("dialogs_list")
                .child(user.getUid())
                .child(dialogItem.getDialog_uid())
                .setValue(dialogItem);

        dialogItem.setName(encode(user.getDisplayName()));
        databaseReference.child("dialogs_list")
                .child(friendItem.getUid())
                .child(dialogItem.getDialog_uid())
                .setValue(dialogItem);
    }


}
