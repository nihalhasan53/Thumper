package com.beautycoder.applicationlockscreenexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}