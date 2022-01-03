package com.example.crytocurrency_ltdt;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class fgment_setting extends PreferenceFragmentCompat {



    //Configuration configuration;
    public static final String My_share_Pref = "setting";
    Context Ncontext;
    public String language , notifVal;
    public String textsize;
    public String dark_mode;
    Intent noftiIntent; /*= new Intent(getActivity() ,News_notification.class);*/
    PendingIntent pendingIntent ;/*= PendingIntent.getBroadcast(getActivity(),0,noftiIntent,PendingIntent.FLAG_UPDATE_CURRENT);*/
    AlarmManager alarmManager; /*= (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);*/
    long currentTime = System.currentTimeMillis();
    long minute = 1000*60;


    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(R.color.negative_red, typedValue, true);

        @ColorInt int color = typedValue.data;

        view.setBackgroundColor(color);

        super.onViewCreated(view, savedInstanceState);
    }*/

//SharedPreferences shP = PreferenceManager.getDefaultSharedPreferences(getActivity());


    @Override
    public void onAttach(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
         noftiIntent = new Intent(getActivity() ,News_notification.class);
         pendingIntent = PendingIntent.getBroadcast(getActivity(),0,noftiIntent,PendingIntent.FLAG_UPDATE_CURRENT);
         alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        super.onAttach(Util.adjustFontSize(context , f));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {


        setPreferencesFromResource(R.xml.setting, rootKey);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Context context = Util.changeLang(getActivity().getBaseContext(), lang_code , f);

        notifVal = sharedPreferences.getString("time_notification","15");
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


        /*THONG BAO ON*/
        /*SwitchPreference nofti = (SwitchPreference) findPreference("turn_on_notification");

        nofti.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean noftiBool =  sharedPreferences.getBoolean("turn_on_notification", false);
            //activitive_screen_main.setlocal(language);
            if (noftiBool) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime,minute*(Integer.parseInt(notifVal)),pendingIntent);
            }
            else {
                if (alarmManager!= null) {
                    alarmManager.cancel(pendingIntent);
                }
            }
            return true;
        });

        SET TIME THÔNG BÁO

        ListPreference Listnotif = (ListPreference)  findPreference("time_notification");

        Listnotif.setOnPreferenceChangeListener((preference, newValue) -> {
             notifVal =  String.valueOf(newValue);
             if(notifVal.contains("5"))
                 alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,currentTime,1000*10,pendingIntent);
             else {
                 alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime, minute * (Integer.parseInt(notifVal)), pendingIntent);
             }
             return true;
        });*/


        Preference BtnIntro = (Preference) findPreference("Intro");
        BtnIntro.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String key = preference.getKey();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("sniper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("intrudution",false);
                editor.commit();
                Intent intent = new Intent(getActivity(), introduction.class);
                startActivity(intent);
                /*Toast.makeText(getActivity(), key, Toast.LENGTH_LONG).show();*/
                getActivity().finish();
                return true;
            }
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