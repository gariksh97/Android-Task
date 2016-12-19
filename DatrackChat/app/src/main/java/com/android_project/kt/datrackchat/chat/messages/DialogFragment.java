package com.android_project.kt.datrackchat.chat.messages;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.chat.dialogs.DialogItem;
import com.android_project.kt.datrackchat.firebase.FirebaseRequests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DialogFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.dialog_fragment_layout;
    private DialogItem dialog;
    private View rootView;
    private boolean isMessageTryToSend;
    private boolean isChangedText;

    public void setDialog(DialogItem dialog) {
        this.dialog = dialog;
        restart();
    }

    private void restart() {
        isMessageTryToSend = false;
        isChangedText = false;
        DatabaseReference messagesDatabaseReference =
                FirebaseDatabase.getInstance().getReference();
        if (rootView != null) {
            if (adapter != null) {
                adapter.cleanup();
            }
            adapter = new FirebaseRecyclerAdapter<MessageItem, DialogFragment.ChatMessageViewHolder>(
                    MessageItem.class,
                    R.layout.message_item,
                    DialogFragment.ChatMessageViewHolder.class,
                    FirebaseRequests.getDialog(dialog.getDialog_uid())) {

                @Override
                protected void populateViewHolder
                        (DialogFragment.ChatMessageViewHolder viewHolder, MessageItem model, int position) {
                    viewHolder.message.setText(model.getText());
                    viewHolder.username.setText(FirebaseRequests.decode(model.getName()));
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

    public DialogFragment() {
    }


    public static Fragment newInstance() {
        DialogFragment fragment = new DialogFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    private FirebaseRecyclerAdapter<MessageItem, DialogFragment.ChatMessageViewHolder>
            adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private Button sendButton;
    private EditText sendMessageText;

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
        rootView = inflater.inflate(R_LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.message_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        sendButton = (Button) rootView.findViewById(R.id.send_button);
        sendMessageText = (EditText) rootView.findViewById(R.id.send_message_text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (sendMessageText.getText().toString().trim().equals(""))
                    return;

                boolean anyNotDatrackWord = false;

                if (!isMessageTryToSend) {
                    StringBuilder newMessageText = new StringBuilder();
                    String text = sendMessageText.getText().toString();
                    String parts[] = text.split("\\s+");
                    String words[] = new String[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        words[i] = parts[i].replaceAll("[^\\w]", "");
                        if (!words[i].equals("") && true) {
                            anyNotDatrackWord = true;
                            newMessageText.append("<font color=#63a34a>")
                                    .append(parts[i])
                                    .append("</font>").append(" ");
                        } else {
                            newMessageText.append(parts[i]).append(" ");
                        }
                    }
                    if (anyNotDatrackWord) {
                        Toast.makeText(
                                getContext(),
                                "Some words are not in datrack",
                                Toast.LENGTH_LONG
                        ).show();
                        sendMessageText.setText(Html.fromHtml(
                                newMessageText.toString()
                        ));
                        isMessageTryToSend = true;
                        isChangedText = false;
                    } else {
                        sendMessage();
                        isMessageTryToSend = false;
                    }
                } else {
                    sendMessage();
                    isMessageTryToSend = false;
                }
            }
        });

        sendMessageText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        isMessageTryToSend = false;
                        if (!isChangedText) {
                            StringBuilder newMessageText = new StringBuilder();
                            String text = sendMessageText.getText().toString();
                            String parts[] = text.split("\\s+");
                            for (int iter = 0; iter < parts.length; iter++) {
                                newMessageText.append(parts[iter]).append(" ");
                            }
                            sendMessageText.setText(newMessageText.toString());
                            isChangedText = true;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
        restart();

        return rootView;
    }

    public DialogItem getDialog() {
        return dialog;
    }

    private void sendMessage() {
        MessageItem newMessage = new
                MessageItem(sendMessageText.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
        );

        isChangedText = true;
        isMessageTryToSend = false;
        FirebaseRequests.pushMessage(getDialog(), newMessage);
        sendMessageText.setText("");
    }
}