package com.example.crytocurrency_ltdt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("news_cryto", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Context context = Util.changeLang(newBase, lang_code ,f);
        //super.attachBaseContext(Util.adjustFontSize(newBase ,f));
        super.attachBaseContext(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Util.adjustFontSize(this ,f);
        setContentView(R.layout.activity_main);
        String language = sharedPreferences.getString("Language", "Vi");
        /*Toast.makeText(MainActivity.this, language, Toast.LENGTH_SHORT).show();*/

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