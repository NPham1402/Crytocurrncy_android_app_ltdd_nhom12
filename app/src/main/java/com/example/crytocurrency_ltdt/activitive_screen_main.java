package com.example.crytocurrency_ltdt;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.utils.Utils;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eu.long1.spacetablayout.SpaceTabLayout;

public class activitive_screen_main extends AppCompatActivity {

    private static Context context;
    private static Resources resources;

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));

        Context context = Util.changeLang(newBase, lang_code,f);
        //Util.adjustFontSize(newBase, f);
        super.attachBaseContext(context);
    }


    SpaceTabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
         Util.adjustFontSize(this,f);
         String dark_mode = sharedPreferences.getString("dark_mode", "MODE_NIGHT_FOLLOW_SYSTEM");
            if (dark_mode.contains("MODE_NIGHT_NO")) {
                Toast.makeText(this, R.string.dark_mode_light, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if (dark_mode.contains("MODE_NIGHT_YES")) {
                Toast.makeText(this, R.string.dark_mode_dark, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else if (dark_mode.contains("MODE_NIGHT_FOLLOW_SYSTEM")) {
                Toast.makeText(this, R.string.dark_mode_system, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            else if (dark_mode.contains("MODE_NIGHT_AUTO_TIME")) {
                Toast.makeText(this, R.string.dark_mode_time, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
            }
            else if (dark_mode.contains("MODE_NIGHT_AUTO_BATTERY")) {
                Toast.makeText(this, R.string.dark_mode_pin, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }


        setContentView(R.layout.activity_activitive_screen_main);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new fgment_news());
        fragmentList.add(new fgment_cryto()                                                                                                                                                                      );
        fragmentList.add(new fgment_setting());
        ViewPager viewPager = (ViewPager) findViewById(R.id.vtl_main);
        tabLayout = (SpaceTabLayout) findViewById(R.id.stl_main);
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        viewPager.setOnTouchListener(new traslate_hiden_scoller_view(getBaseContext(),tabLayout));

        if(Localehelper.getLanguage(activitive_screen_main.this).equalsIgnoreCase("EN"))
        {
            Localehelper.setLocale(activitive_screen_main.this,"en");
        }else if(Localehelper.getLanguage(activitive_screen_main.this).equalsIgnoreCase("VN")) {
            Localehelper.setLocale(activitive_screen_main.this, "vi");
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }


    public static void  setlocale(Context context, String langcode)
    {
        Locale local=new Locale(langcode);
        Locale.setDefault(local);
        Resources res= context.getResources();
       // Configuration configuration = context.getResources().getConfiguration();
        Configuration configuration = res.getConfiguration();
//        Configuration configuration= new Configuration();//res.getConfiguration();
        //getActivity().createConfigurationContext(configuration);
        context.createConfigurationContext(configuration);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {configuration.setLocale(local);}
        else {
            configuration.locale = new Locale(langcode);
        }
        res.updateConfiguration(configuration,res.getDisplayMetrics());

    }

    /*public static void setlocal(String newString) {
        if (newString.contains("EN")) {
            context = Localehelper.setLocale(MyApplication.getAppContext(), "en");
            resources = context.getResources();


        } else if (newString.contains("VN")) {
            context = Localehelper.setLocale(MyApplication.getAppContext(), "vi");
            resources = context.getResources();
        }
    }*/
}