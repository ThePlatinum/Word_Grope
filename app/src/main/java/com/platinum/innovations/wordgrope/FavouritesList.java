package com.platinum.innovations.wordgrope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class FavouritesList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    public class NormalViewHolder extends RecyclerView.ViewHolder{

        TextView Word, Date , N_Results ;

        NormalViewHolder(View itemView) {
            super(itemView);
            Word = itemView.findViewById(R.id.textWord);
            Date = itemView.findViewById(R.id.dateText);
            N_Results = itemView.findViewById(R.id.number_results);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        FooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.footer_text);
        }
    }

    private List<Recents> recents;

    FavouritesList(List<Recents> recents) {
        this.recents = recents;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == recents.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites,parent,false);
            return new NormalViewHolder(v);
        }
        else {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_footer, parent, false);
            return new FooterViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NormalViewHolder) {
            NormalViewHolder NormalHolder = (NormalViewHolder) holder;
            Recents mRecents = recents.get(position);
            NormalHolder.Word.setText(mRecents.getWord());
            NormalHolder.Date.setText(mRecents.getDate());
            NormalHolder.N_Results.setText(String.valueOf(mRecents.getN_Words()));
        }
        else if (holder instanceof FooterViewHolder) {
            FooterViewHolder itemViewHolder = (FooterViewHolder) holder;
            itemViewHolder.footerText.getText();
        }

    }
    @Override
    public int getItemCount() {
        return recents.size()+1;
    }
}
