package com.android_project.kt.datrackchat.chat.persons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//TODO:Chat
public class PersonListFragment extends Fragment {
    private static final int R_LAYOUT = R.layout.personlist_fragment_layout;

    private DatabaseReference
            personDatabaseReference;


    private FirebaseRecyclerAdapter
            <PersonItem, PersonListFragment.PersonViewHolder>
            adapter;

    private RecyclerView
            recyclerView;

    private LinearLayoutManager
            layoutManager;


    private static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        View view;

        public PersonViewHolder(View v) {
            super(v);
            this.view = v;
            Log.d("MyLog", v.getClass().toString());
            userName = (TextView) itemView.findViewById(R.id.person_name);
        }

        public void setListener(final FragmentManager manager, final String friendName) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*DialogFragment fragment = new DialogFragment();

                    FragmentTransaction transaction = .beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.chat_fragment_layout, fragment);
                    transaction.commit();

                    manager.executePendingTransactions();*/
                }
            });
        }
    }

    public PersonListFragment() {}


    public static Fragment newInstance(String value) {
        PersonListFragment fragment = new PersonListFragment();

        Bundle args = new Bundle();
        args.putString("Key", value);
        fragment.setArguments(args);
        Log.d("MyLog", "New Instance");

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R_LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.person_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("MyLog", getArguments().get("Key").toString());

        personDatabaseReference = FirebaseDatabase.getInstance().getReference();
        adapter = new FirebaseRecyclerAdapter<PersonItem, PersonViewHolder>(
                PersonItem.class,
                R.layout.person_item,
                PersonViewHolder.class,
                personDatabaseReference.child(MainActivity.userName).child("friends_list")) {

            @Override
            protected void populateViewHolder(PersonViewHolder viewHolder, PersonItem model, int position) {
                viewHolder.userName.setText(model.getName());
                //viewHolder.setListener(rootFragmentManager, model.getName());
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

        return rootView;
    }
}