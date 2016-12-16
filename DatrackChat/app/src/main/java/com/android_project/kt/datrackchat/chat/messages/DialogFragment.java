package com.android_project.kt.datrackchat.chat.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DialogFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.dialog_fragment_layout;
    private String friendName;

    public DialogFragment() {}


    public static Fragment newInstance(String friendName) {
        DialogFragment fragment = new DialogFragment();

        Bundle args = new Bundle();
        args.putString("friendName", friendName);
        fragment.setArguments(args);

        Log.d("MyLog", "New Instance");

        return fragment;
    }

    private DatabaseReference
            messagesDatabaseReference;

    private FirebaseRecyclerAdapter<MessageItem, DialogFragment.ChatMessageViewHolder>
            adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private Button sendButton;
    private EditText sendMessageText;

    private Long lastMessagePos;

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView username;

        public ChatMessageViewHolder(View v) {
            super(v);
            message = (TextView) itemView.findViewById(R.id.message_text);
            username = (TextView) itemView.findViewById(R.id.message_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R_LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.message_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        friendName = getArguments().getString("friendName");

        messagesDatabaseReference = FirebaseDatabase.getInstance().getReference();

        adapter = new FirebaseRecyclerAdapter<MessageItem, DialogFragment.ChatMessageViewHolder>(
                MessageItem.class,
                R.layout.message_item,
                DialogFragment.ChatMessageViewHolder.class,
                messagesDatabaseReference.child(MainActivity.userName).
                        child("dialogs").child(friendName).child("messages")) {

            @Override
            protected void populateViewHolder(DialogFragment.ChatMessageViewHolder viewHolder, MessageItem model, int position) {
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

        sendButton = (Button) rootView.findViewById(R.id.send_button);
        sendMessageText = (EditText) rootView.findViewById(R.id.send_message_text);

        messagesDatabaseReference.child(MainActivity.userName).child("dialogs").
                child(friendName).child("messages").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object value = dataSnapshot.getValue();
                        if (value != null) {
                            List messages = (List) value;
                            lastMessagePos = (long) messages.size();
                        } else {
                            lastMessagePos = 0L;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageItem newMessage = new
                        MessageItem(sendMessageText.getText().toString(),
                        MainActivity.userName);

                messagesDatabaseReference.child(MainActivity.userName).child("dialogs").
                        child(friendName).
                        child("messages").child(String.valueOf(lastMessagePos)).
                        setValue(newMessage);
                messagesDatabaseReference.child(friendName).child("dialogs").
                        child(MainActivity.userName).
                        child("messages").child(String.valueOf(lastMessagePos)).
                        setValue(newMessage);

                sendMessageText.setText("") ;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return rootView;
    }
}