package com.android_project.kt.datrackchat.chat.dialogs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_project.kt.datrackchat.MainActivity;
import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.chat.messages.DialogFragment;
import com.android_project.kt.datrackchat.firebase.FirebaseRequests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

//TODO:Chat
public class DialogListFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.personlist_fragment_layout;

    private FirebaseRecyclerAdapter
            <DialogItem, DialogViewHolder>
            firebaseAdapter;

    private RecyclerView
            recyclerView;

    private LinearLayoutManager
            layoutManager;

    private static class DialogViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        View view;

        public DialogViewHolder(View v) {
            super(v);
            this.view = v;
            userName = (TextView) itemView.findViewById(R.id.person_name);
        }

        public void setListener(final DialogItem model, final MainActivity activity) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.isDialog = true;
                    activity.changeFragment("Dialog", 0);
                    ((DialogFragment) activity.fragmentMap.get("Dialog"))
                            .setDialog(model);

                }
            });
        }
    }

    public DialogListFragment() {
    }


    public static Fragment newInstance
            (MainActivity activity) {
        DialogListFragment fragment = new DialogListFragment();

        Bundle args = new Bundle();
        args.putParcelable("activity", activity);
        fragment.setArguments(args);
        Log.d("MyLog", "New Instance");

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return inflater.inflate(
                    R.layout.not_auth_fragment_layout, container, false
            );
        }

        final View rootView = inflater.inflate(R_LAYOUT, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.person_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final MainActivity mainActivity = getArguments().getParcelable("activity");

        firebaseAdapter = new FirebaseRecyclerAdapter<DialogItem, DialogViewHolder>(
                DialogItem.class,
                R.layout.person_item,
                DialogViewHolder.class,
                FirebaseRequests.getCurrentUserDialogs()) {

            @Override
            protected void populateViewHolder(DialogViewHolder viewHolder, DialogItem model, int position) {
                viewHolder.userName.setText(FirebaseRequests.decode(model.getName()));
                viewHolder.setListener(model, mainActivity);
            }
        };

        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseAdapter);
        return rootView;
    }
}