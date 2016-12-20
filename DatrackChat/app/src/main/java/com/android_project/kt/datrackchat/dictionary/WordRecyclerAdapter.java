package com.android_project.kt.datrackchat.dictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_project.kt.datrackchat.R;
import com.android_project.kt.datrackchat.models.Word;

import java.util.List;

/**
 * Created by danilskarupin on 19.12.16.
 */

class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder> {

    @NonNull
    private List<Word> data;

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final Context context;

    public WordRecyclerAdapter(@NonNull Context context,
                               @NonNull List<Word> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        setHasStableIds(false);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WordViewHolder.newInstance(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int pos) {
        final Word word = data.get(pos);
        holder.nativeWord.setText(word.nativeWord);
        holder.russianWord.setText(word.russianWord);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        final TextView nativeWord;
        final TextView russianWord;

        WordViewHolder(View view) {
            super(view);
            nativeWord = (TextView) view.findViewById(R.id.nativeWord);
            russianWord = (TextView) view.findViewById(R.id.russianWord);
        }

        static WordViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.dictionary_item, parent, false);
            return new WordViewHolder(view);
        }
    }

}
