package com.platinum.innovations.wordgrope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter {

    private String[] mDataset;

    ResultsAdapter(String[] Dataset) {
        mDataset = Dataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.results_recycler);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View wordView = inflater.inflate(R.layout.results_card, parent, false);

        // Return a new holder instance
        return new MyViewHolder(wordView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String dataset = mDataset[position];
        TextView textView = (TextView) holder.itemView;
        textView.setText(dataset);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
