package com.platinum.innovations.wordgrope;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    List<Dataset> dataset = new ArrayList<>();
    ProgressDialog progressDialog;
    TextView textView;
    String searched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textView = findViewById(R.id.searched_text);
        RecyclerView recyclerView = findViewById(R.id.results_recycler);

        searched = getIntent().getStringExtra("SearchedText");
        textView.setText(searched);

        AsyncTaskUsed asyncTask = new AsyncTaskUsed();
        asyncTask.execute(searched);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new ResultsAdapter(dataset);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Function for swapping the characters at position I with character at position j

    List<Dataset> mDataset = new ArrayList<>();

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
        //Prints the permutations
        Dataset dataset = new Dataset(str);
        mDataset.add(dataset);

//        Toast.makeText(this.getApplicationContext(),str,Toast.LENGTH_SHORT).show();

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
}
