package com.example.crytocurrency_ltdt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
            Intent intent=new Intent(MainActivity.this,activitive_screen_main.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_to_left, R.anim.slide_out_right);
            finish();
            }}, 3000);
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_to_left,
//                R.anim.slide_out_right);
//    }
}