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




//SharedPreferences shP = PreferenceManager.getDefaultSharedPreferences(getActivity());



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {


        setPreferencesFromResource(R.xml.setting, rootKey);

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
            return true;
                    //activitive_screen_main.setlocal(language);
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