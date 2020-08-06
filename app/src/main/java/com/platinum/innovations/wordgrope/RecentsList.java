package com.platinum.innovations.wordgrope;

import android.content.Context;
import android.content.Intent;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String click_identifier =  Word.getText().toString();
                    Context context = v.getContext();
                    Intent i = new Intent(context, ResultsActivity.class);
                    i.putExtra("SearchedText",click_identifier);
                    context.startActivity(i);
                }
            });
        }
    }

    private List<Recents> recents;

    RecentsList(List<Recents> recents) {
        this.recents = recents;
    }

    @NonNull
    @Override
    public RecentsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recents, parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recents mRecents = recents.get(position);
        holder.Word.setText(mRecents.getWord());
        holder.Date.setText(mRecents.getDate());
        holder.N_Results.setText(String.valueOf(mRecents.getN_Words()));
    }
    @Override
    public int getItemCount() {
        return recents.size();
    }
}
