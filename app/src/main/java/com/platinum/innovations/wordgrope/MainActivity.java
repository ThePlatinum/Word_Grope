package com.platinum.innovations.wordgrope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ActicityMainBinding bindingMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMain = ResultProfileBinding.inflate(getLayoutInflater());
        View view = bindingMain.getRoot();
        setContentView(view);
    }
}
