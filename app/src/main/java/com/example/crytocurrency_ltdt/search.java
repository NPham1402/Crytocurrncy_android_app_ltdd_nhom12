package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

public class search extends AppCompatActivity {
    SearchView search;
    serch_adapter Adapter;
    ArrayList<String> tickers;
    Util util;

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

        if(Localehelper.getLanguage(search.this).equalsIgnoreCase("EN"))
        {
            Localehelper.setLocale(search.this,"en");
        }else if(Localehelper.getLanguage(search.this).equalsIgnoreCase("VN")) {
            Localehelper.setLocale(search.this, "vi");
        }

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Util.adjustFontSize(this ,f);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.searchView);
        util =new Util(this);
        tickers = new ArrayList<String>();
        Adapter=new serch_adapter(tickers,this);
        search.setIconifiedByDefault(true);
        search.setFocusable(true);
        search.setIconified(false);
        search.requestFocusFromTouch();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
    }
    private void search(String textSearch){
        ArrayList<String> searchList = new ArrayList<String>();;
        for (String furniture:util.getticker()){
            if (furniture.toLowerCase().contains(textSearch.toLowerCase())){
                searchList.add(furniture);
            }
        }
        if (searchList.size()>0){
            Adapter .clear();
            Adapter.addlist(searchList);
            Adapter.notifyDataSetChanged();
        }

    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm
                =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}