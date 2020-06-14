package com.platinum.innovations.wordgrope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // private ActivityMainBinding bindingMain;
    SearchView searchView;
    AdView mAdView;
    DBHelper dbHelper;
    RecyclerView recent_recycler;
    List<Recents> mRecents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bindingMain = ResultProfileBinding.inflate(getLayoutInflater());
        //View view = bindingMain.getRoot();

        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search);

        //loadLists();

        //Ads Related
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.VISIBLE);

        //Searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, ResultsActivity.class);
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


    private void loadLists() {
        Cursor cursor = dbHelper.getAllSearched();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int n_results = cursor.getInt(cursor.getColumnIndex("results"));

                Recents recents = new Recents(word, date, n_results);
                mRecents.add(recents);

                cursor.moveToNext();
            }
            cursor.close();
        }
        dbHelper.close();

        recent_recycler = findViewById(R.id.recycler_recent);
        recent_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recent_recycler.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new RecentsList(mRecents);
        mAdapter.notifyDataSetChanged();
        recent_recycler.setAdapter(mAdapter);
    }
}
