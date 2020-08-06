package com.platinum.innovations.wordgrope;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class AllFavouriteActivity extends AppCompatActivity {

    RecyclerView allFav;
    DBHelper dbHelper;
    List<Favourites> mFavourites = new ArrayList<>();
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_favourite);

        allFav = findViewById(R.id.recycler_all_fav);
        dbHelper = new DBHelper(this);

        //Ads Related
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.f_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Cursor cursor = dbHelper.getAllFavourites();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int n_results = cursor.getInt(cursor.getColumnIndex("results"));

                Favourites favourites = new Favourites(word, date, n_results);
                mFavourites.add(favourites);

                cursor.moveToNext();
            }
            cursor.close();
        }
        dbHelper.close();

        allFav.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allFav.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new FavouritesList(mFavourites);
        mAdapter.notifyDataSetChanged();
        allFav.setAdapter(mAdapter);
    }
}
