package com.example.crytocurrency_ltdt;

import android.app.Activity;
import android.content.Context;
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
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        Context context = Util.changeLang(newBase, lang_code);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String language = sharedPreferences.getString("Language", "VN");
        Toast.makeText(MainActivity.this, language, Toast.LENGTH_SHORT).show();

        if(Localehelper.getLanguage(MainActivity.this).equalsIgnoreCase("EN"))
        {
             Localehelper.setLocale(MainActivity.this,"en");
        }else if(Localehelper.getLanguage(MainActivity.this).equalsIgnoreCase("VN")) {
            Localehelper.setLocale(MainActivity.this, "vi");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, activitive_screen_main.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_out_right);
                finish();
            }
        }, 3000);
    }



    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
       overridePendingTransition(R.anim.slide_to_left,
               R.anim.slide_out_right);
    }*/
}