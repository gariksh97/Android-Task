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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android_project.kt.datrackchat.ArgumentsBundle;
import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.chat.dialogs.DialogItem;
import com.android_project.kt.datrackchat.firebase.FirebaseRequests;
import com.android_project.kt.datrackchat.managers.DictionaryManager;
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

    private FirebaseRecyclerAdapter<MessageItem, DialogFragment.ChatMessageViewHolder>
            adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private Button sendButton;
    private EditText sendMessageText;

    public void setDialog(DialogItem dialog) {
        this.dialog = dialog;
        adapter = null;
    }

    private void restart() {
        if (adapter == null) {
            isMessageTryToSend = false;
            isChangedText = true;
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
        sendButton = (Button) rootView.findViewById(R.id.send_button);
        sendMessageText = (EditText) rootView.findViewById(R.id.send_message_text);

        final DictionaryManager dictionaryManager = new DictionaryManager();
        final MainActivity mainActivity = (MainActivity) getActivity();
        dictionaryManager.getWholeDictionary(mainActivity);
        if (savedInstanceState == null) {
            Log.d(TAG, "start");
            restart();
        } else {
            ArgumentsBundle bundle = (ArgumentsBundle) savedInstanceState.get("arguments");
            Log.d(TAG, "load");
            sendMessageText.setText(
                    (String) bundle.get("sendMessageText")
            );
            sendMessageText.setSelection(
                    (int) bundle.get("sendMessageTextPos")
            );
            adapter = (FirebaseRecyclerAdapter<MessageItem, ChatMessageViewHolder>)
                    bundle.get("adapter");
            dialog = (DialogItem) bundle.get("dialog");
            isMessageTryToSend = (boolean) bundle.get("isMessageTryToSend");
            isChangedText = (boolean) bundle.get("isChangeText");
        }

        sendButton.setOnClickListener(
                new View.OnClickListener() {
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
                                if (!words[i].equals("") &&
                                        dictionaryManager.checkWord(words[i], mainActivity)) {
                                    newMessageText.append(parts[i]).append(" ");

                                } else if (i + 1 != parts.length &&
                                        !words[i].equals("") && !words[i + 1].equals("") &&
                                        dictionaryManager.checkWord(
                                                words[i] + " " + words[i + 1],
                                                mainActivity
                                        )) {
                                    newMessageText.append(parts[i]).append(" ");
                                } else {
                                    anyNotDatrackWord = true;
                                    newMessageText.append("<font color=#63a34a>")
                                            .append(parts[i])
                                            .append("</font>").append(" ");
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
                        int pos = sendMessageText.getSelectionStart();
                        if (!isChangedText) {
                            isChangedText = true;
                            StringBuilder newMessageText = new StringBuilder();
                            String text = sendMessageText.getText().toString();
                            String parts[] = text.split("\\s+");
                            for (int iter = 0; iter < parts.length; iter++) {
                                newMessageText.append(parts[iter]).append(" ");
                            }
                            sendMessageText.setText(newMessageText.toString());
                            sendMessageText.setSelection(Math.min(newMessageText.length(), pos));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


        recyclerView = (RecyclerView) rootView.findViewById(R.id.message_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter = null;

        ArgumentsBundle bundle = new ArgumentsBundle();
        bundle.put("sendMessageText", sendMessageText.getText().toString());
        bundle.put("sendMessageTextPos", sendMessageText.getSelectionStart());
        bundle.put("adapter", adapter);
        bundle.put("dialog", dialog);
        bundle.put("isMessageTryToSend", isMessageTryToSend);
        bundle.put("isChangeText", isChangedText);
        Log.d(TAG, "save");
        outState.putSerializable("arguments", bundle);
    }

    private static String TAG = "DialogFragment";
}