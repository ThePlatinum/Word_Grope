package com.platinum.innovations.wordgrope;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class ResultsActivity extends AppCompatActivity {

    List<Dataset> dataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView textView = findViewById(R.id.searched_text);

        RecyclerView recyclerView = findViewById(R.id.results_recycler);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);

        String searched = getIntent().getStringExtra("SearchedText");
        textView.setText(searched);

        assert searched != null;
        int len = searched.length();
        //Todo: Use a loader

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(70,TRUE);
        }

        //Check
        progressBar.setVisibility(View.VISIBLE);

        generatePermutation(searched, 0, len);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new ResultsAdapter(dataset);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        //Check
        progressBar.setVisibility(View.INVISIBLE);

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
    void generatePermutation(String strr, int start, int end)
    {
        //Prints the permutations
        Dataset dataset = new Dataset(strr);
        mDataset.add(dataset);

        Toast.makeText(this.getApplicationContext(),strr,Toast.LENGTH_SHORT).show();

            for (int i = start; i < end; i++)
            {
                //Swapping the string by fixing a character
                strr = swapString(strr,start,i);
                //Recursively calling function generatePermutation() for rest of the characters
                generatePermutation(strr,start+1,end);
                //Backtracking and swapping the characters again.
                strr = swapString(strr,start,i);
            }

    }
}
