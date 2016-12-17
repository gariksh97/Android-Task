package com.android_project.kt.datrackchat.chat.persons;

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
import com.android_project.kt.datrackchat.SectionsPagerAdapter;
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
            firebaseAdapter;

    private RecyclerView
            recyclerView;

    private LinearLayoutManager
            layoutManager;

    private SectionsPagerAdapter pagerAdapter;


    private static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        View view;

        public PersonViewHolder(View v) {
            super(v);
            this.view = v;
            Log.d("MyLog", v.getClass().toString());
            userName = (TextView) itemView.findViewById(R.id.person_name);
        }

        public void setListener(final SectionsPagerAdapter adapter, final String friendName,
                                final Fragment rootFragment) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = DialogFragment.newInstance(friendName);
                    adapter.changeFragment(rootFragment.getChildFragmentManager(), 0, fragment);

                }
            });
        }
    }

    public PersonListFragment() {}


    public static Fragment newInstance
            (SectionsPagerAdapter adapter) {
        PersonListFragment fragment = new PersonListFragment();

        Bundle args = new Bundle();
        args.putParcelable("adapter", adapter);
        fragment.setArguments(args);
        Log.d("MyLog", "New Instance");

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R_LAYOUT, container, false);

        /*recyclerView = (RecyclerView) rootView.findViewById(R.id.person_list_recycler);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        pagerAdapter = getArguments().getParcelable("adapter");

        personDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAdapter = new FirebaseRecyclerAdapter<PersonItem, PersonViewHolder>(
                PersonItem.class,
                R.layout.person_item,
                PersonViewHolder.class,
                personDatabaseReference.child(MainActivity.userName).child("friends_list")) {

            @Override
            protected void populateViewHolder(PersonViewHolder viewHolder, PersonItem model, int position) {
                viewHolder.userName.setText(model.getName());
                viewHolder.setListener(pagerAdapter, model.getName(), PersonListFragment.this);
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
        */
        return rootView;
    }
}