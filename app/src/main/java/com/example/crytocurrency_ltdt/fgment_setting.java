package com.example.crytocurrency_ltdt;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class fgment_setting extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting, rootKey);
    }
}