package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class preconfig {
    private static final String listkey = "list_key";

    public static void write(Context context, ArrayList<String> args) {
        Gson gson = new Gson();
        String json = gson.toJson(args);
        SharedPreferences sharedPreferences1 = context.getSharedPreferences("likelist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(listkey, json);
        editor.commit();
    }

    public static ArrayList<String> read(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("likelist", Context.MODE_PRIVATE);
        String jsonstring = sharedPreferences.getString(listkey, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> list=new ArrayList<>();
        list= gson.fromJson(jsonstring, type);
        return list;
    }

    public static void remove(Context context, String a) {
        ArrayList<String> list=new ArrayList<>();
        list.addAll(read(context));
        list.remove(a);
        write(context,list);
    }
    public static Boolean content(Context context, String a) {
        ArrayList<String> list=new ArrayList<>();
        list.addAll(read(context));
        if (list.contains(a))
            return true;
        else
            return false;
    }

}
