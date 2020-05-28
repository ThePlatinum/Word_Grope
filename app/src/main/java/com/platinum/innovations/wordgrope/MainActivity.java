package com.platinum.innovations.wordgrope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // private ActicityMainBinding bindingMain;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bindingMain = ResultProfileBinding.inflate(getLayoutInflater());
        //View view = bindingMain.getRoot();
        setContentView(R.layout.activity_main);

        searchView = (SearchView) findViewById(R.id.search);

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
