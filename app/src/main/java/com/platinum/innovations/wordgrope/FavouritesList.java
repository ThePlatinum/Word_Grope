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

class FavouritesList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public class NormalViewHolder extends RecyclerView.ViewHolder{

        TextView Word, Date , N_Results ;
        NormalViewHolder(View itemView) {
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

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        FooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.footer_text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, AllFavouriteActivity.class);
                    context.startActivity(i);
                }
            });
        }
    }

    private List<Favourites> favs;

    FavouritesList(List<Favourites> favs) {
        this.favs = favs;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 7) {
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
            Favourites mFavs = favs.get(position);
            NormalHolder.Word.setText(mFavs.getWord());
            NormalHolder.Date.setText(mFavs.getDate());
            NormalHolder.N_Results.setText(String.valueOf(mFavs.getN_Words()));
        }
        else if (holder instanceof FooterViewHolder) {
            FooterViewHolder itemViewHolder = (FooterViewHolder) holder;
            itemViewHolder.footerText.getText();
        }
    }
    @Override
    public int getItemCount() {
        return favs.size();
    }
}
