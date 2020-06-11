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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    List<Dataset> mDataset = new ArrayList<>();
    ProgressDialog progressDialog;
    FloatingActionButton fab;
    TextView textView;
    String searched;
    DBHelper searchDB;
    int numberOfResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textView = findViewById(R.id.searched_text);

        //Get what was searched
        searched = getIntent().getStringExtra("SearchedText");
        textView.setText(searched);

        //Get date and time
        String current_date = get_Date();

        //Using AsyncTask to load the results and show progressBar while it is loading
        AsyncTaskUsed asyncTask = new AsyncTaskUsed();
        asyncTask.execute(searched);

        //Save search to data base
        numberOfResults = mDataset.size();
        searchDB.insertSearched(searched, java.sql.Date.valueOf(current_date),numberOfResults);


        RecyclerView recyclerView = findViewById(R.id.results_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ResultsAdapter(mDataset);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
            //progressDialog.show(); // Display Progress Dialog
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
           // progressDialog.hide();
           // progressDialog.dismiss();
        }
    }


    private String get_Date(){
        return DateFormat.getDateInstance().format(new Date());
    }

}
