package com.example.crytocurrency_ltdt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String language=sharedPreferences.getString("Language","VN");
        Toast.makeText(MainActivity.this, language, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if(language.contains("VN"))
                {
                    Toast.makeText(MainActivity.this,"Chay tieng viet", Toast.LENGTH_SHORT).show();
                    setlocal( MainActivity.this,"vi");
                }
                if(language.contains("EN"))
                {
                    Toast.makeText(MainActivity.this, "chay tieng anh", Toast.LENGTH_SHORT).show();
                    setlocal(MainActivity.this,"en");
                }
                Intent intent=new Intent(MainActivity.this,activitive_screen_main.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_out_right);
            finish();
            }}, 3000);
    }
    public void setlocal(Activity activity,String langcode)
    {
        Locale local=new Locale(langcode);
        local.setDefault(local);
        Resources res=activity.getResources();
        Configuration configuration=res.getConfiguration();
        configuration.setLocale(local);
        res.updateConfiguration(configuration,res.getDisplayMetrics());
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_to_left,
//                R.anim.slide_out_right);
//    }
}