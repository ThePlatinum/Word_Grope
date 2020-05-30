package com.platinum.innovations.wordgrope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    // private ActicityMainBinding bindingMain;
    SearchView searchView;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bindingMain = ResultProfileBinding.inflate(getLayoutInflater());
        //View view = bindingMain.getRoot();
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);

        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        searchView = findViewById(R.id.search);

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
}
