package com.platinum.innovations.wordgrope;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SearchView;

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

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    DBHelper dbHelper = new DBHelper(this);
    List<Recents> mRecents = new ArrayList<>();
    List<Favourites> mFavourites = new ArrayList<>();
    RecyclerView recent_recycler ;
    RecyclerView favourites_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search);
        AdView mAdView = findViewById(R.id.adView);

        //Ads Related
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadListsFavourites();
        loadListsRecents();

        //Searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                query = query.replaceAll("\\s","");
                i.putExtra("SearchedText",query);
                startActivity(i);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadListsFavourites();
        loadListsRecents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private void loadListsRecents() {
        Cursor cursorR = dbHelper.getAllSearched();
        if (cursorR != null) {
            cursorR.moveToFirst();
            while (!cursorR.isAfterLast()) {
                String word = cursorR.getString(cursorR.getColumnIndex("word"));
                String date = cursorR.getString(cursorR.getColumnIndex("date"));
                int n_results = cursorR.getInt(cursorR.getColumnIndex("results"));

                Recents recents = new Recents(word, date, n_results);
                mRecents.add(recents);

                cursorR.moveToNext();
            }
            cursorR.close();
        }
        dbHelper.close();

        recent_recycler = findViewById(R.id.recycler_recent);
        recent_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recent_recycler.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter sAdapter = new RecentsList(mRecents);
        sAdapter.notifyDataSetChanged();
        recent_recycler.setAdapter(sAdapter);
    }

    private void loadListsFavourites() {
        Cursor cursor = dbHelper.getLimitFavourites();
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

        favourites_recycler = findViewById(R.id.recycler_favourite);
        favourites_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        favourites_recycler.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter =  new FavouritesList(mFavourites);
        adapter.notifyDataSetChanged();
        favourites_recycler.setAdapter(adapter);
    }
}
