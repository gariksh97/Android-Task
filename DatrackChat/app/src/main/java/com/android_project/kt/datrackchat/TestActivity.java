package com.android_project.kt.datrackchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android_project.kt.datrackchat.chat.dialogs.DialogItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by garik on 16.12.16.
 */

public class TestActivity extends AppCompatActivity {

    private static final int R_LAYOUT = R.layout.personlist_fragment_layout;

    private DatabaseReference
            personDatabaseReference;


    private FirebaseRecyclerAdapter<DialogItem, TestActivity.PersonViewHolder>
            firebaseAdapter;

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

        /*public void setListener(final SectionsPagerAdapter adapter, final String friendName,
                                final Fragment rootFragment) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = DialogFragment.newInstance(friendName);
                    adapter.changeFragment(rootFragment.getChildFragmentManager(), 0, fragment);

                }
            });
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personlist_fragment_layout);

        recyclerView = (RecyclerView) findViewById(R.id.person_list_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        personDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAdapter = new FirebaseRecyclerAdapter<DialogItem, TestActivity.PersonViewHolder>(
                DialogItem.class,
                R.layout.person_item,
                TestActivity.PersonViewHolder.class,
                personDatabaseReference .child("friends_list")) {

            @Override
            protected void populateViewHolder(TestActivity.PersonViewHolder viewHolder, DialogItem model, int position) {
                viewHolder.userName.setText(model.getName());
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
    }
}
