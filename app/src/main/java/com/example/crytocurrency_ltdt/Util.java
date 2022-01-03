package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;


public class Util {
    Context context;
    ArrayList<Cryto> crytoArrayList;
    ArrayList<searc> arrayticker;
    String myfile= "favorite";
    ArrayList<News> newsArrayList;
    public void writetofile(String coinfarvorite){
        try{
            File file=new File(context.getFilesDir(),myfile);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(coinfarvorite.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  ArrayList<String> loadfromfile(){
        ArrayList<String>arrayList=new ArrayList<>();
        File file=new File(context.getFilesDir(),myfile);
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            arrayList=(ArrayList<String>) objectInputStream.readObject();
            return arrayList;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        return  null;
    }


    public static ContextWrapper changeLang(Context context, String lang_code,float fontco){
        Locale sysLocale;

        Resources rs = context.getResources();
        Configuration config = rs.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }
        if ( config.fontScale != fontco)
            config.fontScale = fontco;
        if (!lang_code.equals("") && !sysLocale.getLanguage().equals(lang_code)) {
            Locale locale = new Locale(lang_code);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config);
            } else {
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
        }

        return new ContextWrapper(context);
    }




    public static Context adjustFontSize(Context context, float fontco){
        Configuration configuration = context.getResources().getConfiguration();
        // This will apply to all text like -> Your given text size * fontScale
        if ( configuration.fontScale != fontco)
        configuration.fontScale = fontco;//1.0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(configuration);
        } else {
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }

        return context; //context.createConfigurationContext(configuration);
    }


//    private static OkHttpClient httpClient;
//
//    // this method is used to fetch svg and load it into target imageview.
//    public static void fetchSvg(Context context, String url, final ImageView target) {
//        if (httpClient == null) {
//            httpClient = new OkHttpClient.Builder()
//                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
//                    .build();
//        }
//
//        // here we are making HTTP call to fetch data from URL.
//        Request request = new Request.Builder().url(url).build();
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // we are adding a default image if we gets any error.
//                target.setImageResource(R.drawable.temp);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                // sharp is a library which will load stream which we generated
//                // from url in our target imageview.
//                InputStream stream = response.body().byteStream();
//                Sharp.loadInputStream(stream).into(target);
//                stream.close();
//            }
//        });
//    }

    public int countcryto(){
        return crytoArrayList.size();
    }
    public  ArrayList<searc> getticker(){

    return  arrayticker;
    }
    public Util(Context context) {
        this.context = context;
    }
    public ArrayList<Cryto> getcrypto(){
        crytoArrayList.clear();
        return crytoArrayList;
    }
    public ArrayList<News> getnews(){
        newsArrayList.clear();
      //  getnews_api();
        /*Toast.makeText(context,""+newsArrayList.size(), Toast.LENGTH_SHORT).show();*/
        return newsArrayList;
    }


    public    ArrayList<News> getnews_api(News_adapter news_adapter){
        String url="https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2";
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data=response.getJSONArray("results");
                    for (int i=0;i<data.length();i++){
                        String sysmbol="";
                        JSONObject dataobject=data.getJSONObject(i);
                        String domain=dataobject.getString("domain");
                        String title=dataobject.getString("title");
                        String date=dataobject.getString("published_at");
                        String url=dataobject.getString("url");
                        newsArrayList.add(new News(title,date,domain,url,"Crypto Panic"));
                    }
                    news_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context , R.string.cannot_retrieve_data, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        return newsArrayList;
    }
public static class searc{
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String uuid;
    Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    String name;

    public searc(String uuid, String name,Boolean status) {
        this.uuid = uuid;
        this.name = name;
        this.status = status;
    }
}

}
