package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.request.RequestOptions;

public class fgment_setting extends PreferenceFragmentCompat {

    public static final String My_share_Pref = "setting";
    Context NCONTEXT;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting, rootKey);
    }

    SharedPreferences shP = PreferenceManager.getDefaultSharedPreferences(getContext());

}