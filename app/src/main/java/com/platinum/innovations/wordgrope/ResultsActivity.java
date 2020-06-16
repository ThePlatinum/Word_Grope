package com.platinum.innovations.wordgrope;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    List<Dataset> mDataset = new ArrayList<>();
    ProgressDialog progressDialog;
    FloatingActionButton fab;
    TextView textView;
    String searched, current_date;
    DBHelper searchDB;
    int numberOfResults;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        searchDB = new DBHelper(this);
        textView = findViewById(R.id.searched_text);

        numberOfResults = 0;

        //Ads Related
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.r_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setVisibility(View.VISIBLE);

        //Get what was searched
        searched = getIntent().getStringExtra("SearchedText");
        textView.setText(searched);

        //Get date and time
        current_date = get_Date();

        //Using AsyncTask to load the results and show progressBar while it is loading
        AsyncTaskUsed asyncTask = new AsyncTaskUsed();
        asyncTask.execute(searched);

        //Save search to data base
        searchDB.insertSearched(searched, current_date, numberOfResults);

        RecyclerView recyclerView = findViewById(R.id.results_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter nAdapter = new ResultsAdapter(mDataset);
        nAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(nAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchDB.isFieldExist(searched)){
                    searchDB.delete(searched);
                    fab.setImageResource(R.drawable.star_off_foreground);
                }
                else{
                    searchDB.insertFavourite(searched, current_date, numberOfResults);
                    fab.setImageResource(R.drawable.star_on_foreground);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    //Function for swapping the characters at position i with character at position j

    private static String swapString(String a, int i, int j) {
        char[] b =a.toCharArray();
        char ch;
        ch = b[i];
        b[i] = b[j];
        b[j] = ch;
        return String.valueOf(b);
    }

    //Function for generating different permutations of the string
    void generatePermutation(String str, int start, int end)
    {

        if (start == end-1) {
            Dataset dataset = new Dataset(str);
            mDataset.add(dataset);
            numberOfResults = numberOfResults + 1;
        }
        else
        {
            for (int i = start; i < end; i++)
            {
                //Swapping the string by fixing a character
                str = swapString(str,start,i);
                //Recursively calling function generatePermutation() for rest of the characters
                generatePermutation(str,start+1,end);
                //Backtracking and swapping the characters again.
                str = swapString(str,start,i);
            }
        }

    }
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskUsed extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ResultsActivity.this);
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setTitle("Getting the Words..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show(); // Display Progress Dialog
        }

        @Override
        protected String doInBackground(String... strings) {
            assert strings[0] != null;
            int len = strings[0].length();
            generatePermutation(strings[0], 0, len);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }


    private String get_Date(){
        return DateFormat.getDateInstance().format(new Date());
    }

}
