package com.example.crytocurrency_ltdt;

import android.util.DisplayMetrics;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;
import java.util.Objects;

public class fgment_setting extends PreferenceFragmentCompat {



    //Configuration configuration;
    public static final String My_share_Pref = "setting";
    Context Ncontext;
    public String language;
    public String textsize;
    public String dark_mode;



//SharedPreferences shP = PreferenceManager.getDefaultSharedPreferences(getActivity());


    @Override
    public void onAttach(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        super.onAttach(Util.adjustFontSize(context , f));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {


        setPreferencesFromResource(R.xml.setting, rootKey);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Context context = Util.changeLang(getActivity().getBaseContext(), lang_code , f);

        Ncontext = this.getContext();

        ListPreference Language = (ListPreference) findPreference("Language");

        Language.setOnPreferenceChangeListener((preference, newValue) -> {
                    language =  String.valueOf(newValue);
            //activitive_screen_main.setlocal(language);
            if (language.contains("vi")) {
                Toast.makeText(getContext(), R.string.Vietnamese, Toast.LENGTH_SHORT).show();
//                activitive_screen_main.setlocale(Ncontext, "vi");
            }
            if (language.contains("en")) {
                Toast.makeText(getContext(), R.string.English, Toast.LENGTH_SHORT).show();
//                activitive_screen_main.setlocale(Ncontext, "en");
            }
            getActivity().recreate();
            return true;
                    //activitive_screen_main.setlocal(language);
                });

        ListPreference Textsize = (ListPreference) findPreference("textsize");

        Textsize.setOnPreferenceChangeListener((preference, newValue) -> {
            textsize =  String.valueOf(newValue);
            //activitive_screen_main.setlocal(language);
            if (textsize.contains("0.5f")) {
                Toast.makeText(getContext(), R.string.co_chu_small, Toast.LENGTH_SHORT).show();
//                activitive_screen_main.setlocale(Ncontext, "vi");
            }
            else if (textsize.contains("1.0f")) {
                Toast.makeText(getContext(), R.string.co_chu_normal, Toast.LENGTH_SHORT).show();
//                activitive_screen_main.setlocale(Ncontext, "en");
            }
            else if (textsize.contains("1.8f")) {
                Toast.makeText(getContext(), R.string.co_chu_big, Toast.LENGTH_SHORT).show();
//                activitive_screen_main.setlocale(Ncontext, "en");
            }
            getActivity().recreate();
            return true;
        });


        ListPreference Dark_mode = (ListPreference) findPreference("dark_mode");
        Dark_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            dark_mode =  String.valueOf(newValue);
            //activitive_screen_main.setlocal(language);
            if (dark_mode.contains("MODE_NIGHT_NO")) {
                Toast.makeText(getContext(), R.string.dark_mode_light, Toast.LENGTH_SHORT).show();
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if (dark_mode.contains("MODE_NIGHT_YES")) {
                Toast.makeText(getContext(), R.string.dark_mode_dark, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else if (dark_mode.contains("MODE_NIGHT_FOLLOW_SYSTEM")) {
                Toast.makeText(getContext(), R.string.dark_mode_system, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            else if (dark_mode.contains("MODE_NIGHT_AUTO_TIME")) {
                Toast.makeText(getContext(), R.string.dark_mode_time, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
            }
            else if (dark_mode.contains("MODE_NIGHT_AUTO_BATTERY")) {
                Toast.makeText(getContext(), R.string.dark_mode_pin, Toast.LENGTH_SHORT).show();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }
            getActivity().recreate();
            return true;
        });

            /*if (language.contains("VN")) {
                Toast.makeText(getContext(), R.string.Vietnamese, Toast.LENGTH_SHORT).show();
                setlocal(getContext(), "vi");
            }
            if (language.contains("EN")) {
                Toast.makeText(getContext(), R.string.English, Toast.LENGTH_SHORT).show();
                setlocal(getContext(), "en");
            }
            return true;
        });*/

        /*Language = findPreference("Language");
        Language.setOnPreferenceClickListener(preference -> {

                String language = Language.getValue();
                if (language.contains("VN")) {
                    Toast.makeText(getContext(), R.string.Vietnamese, Toast.LENGTH_SHORT).show();
                    setlocal(getActivity(), "vi");
                }
                if (language.contains("EN")) {
                    Toast.makeText(getContext(), R.string.English, Toast.LENGTH_SHORT).show();
                    setlocal(getActivity(), "en");
                }
                return true;
        });*/




    /*private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLayoutDirection(locale);
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }*/

    /*public  void  setlocal(Context context, String langcode)
    {
        Locale local=new Locale(langcode);
        Locale.setDefault(local);
        Resources res= context.getResources();
        Configuration configuration = new Configuration(res.getConfiguration());
//        Configuration configuration= new Configuration();//res.getConfiguration();
        configuration.locale = local;
        configuration.setLocale(local);
        //getActivity().createConfigurationContext(configuration);
        res.updateConfiguration(configuration,res.getDisplayMetrics());
    }*/
    }
}