package com.android_project.kt.datrackchat.addfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.firebase.FirebaseRequests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by garik on 18.12.16.
 */

public class AddFriendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FirebaseRecyclerAdapter<FriendItem, NewFriendViewHolder> firebaseAdapter;


    private static class NewFriendViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView email;
        View view;

        public NewFriendViewHolder(View v) {
            super(v);
            this.view = v;
            userName = (TextView) itemView.findViewById(R.id.new_friend_name);
            email = (TextView) itemView.findViewById(R.id.new_friend_email);
        }

        public void setListener(final AddFriendActivity activity, final FriendItem friendItem) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseRequests.addFriend(friendItem);
                    activity.finish();
                }
            });
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_friend_acitivity);

        recyclerView = (RecyclerView) findViewById(R.id.new_friends_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseAdapter = new FirebaseRecyclerAdapter<FriendItem, NewFriendViewHolder>(
                FriendItem.class,
                R.layout.new_friend_item,
                NewFriendViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("users")) {

            @Override
            protected void populateViewHolder(NewFriendViewHolder viewHolder, FriendItem model, int position) {
                viewHolder.userName.setText(model.decodeName());
                viewHolder.email.setText(model.decodeEmail());
                viewHolder.setListener(AddFriendActivity.this, model);
            }
        };

        EditText editText = (EditText) findViewById(R.id.new_friends_edit);
        editText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        firebaseAdapter.cleanup();
                        Log.d("MyLog", charSequence.toString());
                        firebaseAdapter = new FirebaseRecyclerAdapter<FriendItem, NewFriendViewHolder>(
                                FriendItem.class,
                                R.layout.new_friend_item,
                                NewFriendViewHolder.class,
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .orderByChild("email")
                                        .startAt(FirebaseRequests.encode(charSequence.toString()))
                                        .endAt(FirebaseRequests.encode(charSequence.toString()) +
                                                "\uf8ff")) {

                            @Override
                            protected void populateViewHolder
                                    (NewFriendViewHolder viewHolder, FriendItem model, int position) {
                                viewHolder.userName.setText(model.decodeName());
                                viewHolder.email.setText(model.decodeEmail());
                                viewHolder.setListener(AddFriendActivity.this, model);
                            }
                        };
                        recyclerView.setAdapter(firebaseAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseAdapter);
    }

    @Override
    public void finish() {
        startActivity(new Intent(this, MainActivity.class));
        super.finish();
    }
}
