package com.android_project.kt.datrackchat.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference
            messagesdatabaseReference;

    private FirebaseRecyclerAdapter
            <ChatMessage, ChatMessageViewHolder>
            adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private String friend;

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView username;

        public ChatMessageViewHolder(View v) {
            super(v);
            message = (TextView) itemView.findViewById(R.id.message_text);
            username = (TextView) itemView.findViewById(R.id.message_name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        friend = intent.getStringExtra("friend");

        recyclerView = (RecyclerView) findViewById(R.id.message_list_recycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        messagesdatabaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder>(
                ChatMessage.class,
                R.layout.message_item,
                ChatMessageViewHolder.class,
                messagesdatabaseReference.child(MainActivity.userName).child(friend).child("messages")) {

            @Override
            protected void populateViewHolder(ChatMessageViewHolder viewHolder, ChatMessage model, int position) {
                viewHolder.message.setText(model.getText());
                viewHolder.username.setText(model.getName());
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
