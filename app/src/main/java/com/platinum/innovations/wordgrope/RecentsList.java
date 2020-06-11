package com.platinum.innovations.wordgrope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecentsList extends RecyclerView.Adapter<RecentsList.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Word, Date , N_Results ;

        ViewHolder(View itemView) {
            super(itemView);
            Word = itemView.findViewById(R.id.textWord);
            Date = itemView.findViewById(R.id.dateText);
            N_Results = itemView.findViewById(R.id.number_results);
        }
    }

    private List<Recents> recents;

    RecentsList(List<Recents> recents) {
        this.recents = recents;
    }

    @NonNull
    @Override
    public RecentsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recents mRecents = recents.get(position);
        holder.Word.setText(mRecents.getWord());
        holder.Word.setText(mRecents.getDate());
        holder.Word.setText(mRecents.getN_Words());
    }
    @Override
    public int getItemCount() {
        return recents.size();
    }
}
