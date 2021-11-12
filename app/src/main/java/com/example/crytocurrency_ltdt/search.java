package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class search extends AppCompatActivity {
    SearchView search;
    serch_adapter Adapter;
    ArrayList<String> tickers;
    Util util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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