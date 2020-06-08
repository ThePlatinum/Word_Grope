package com.platinum.innovations.wordgrope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.results_recycler);
        }
    }

    private List<Dataset> mDataset;

    public ResultsAdapter(List<Dataset> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View)LayoutInflater.from(parent.getContext()).inflate(R.layout.results_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dataset dataset = mDataset.get(position);
        holder.textView.setText(dataset.getmWord());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
