package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.util.Log;
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
        Toast.makeText(context,""+newsArrayList.size(), Toast.LENGTH_SHORT).show();
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
                        Log.e("lay",domain);
                        String date=dataobject.getString("published_at");
                        String url=dataobject.getString("url");
                        Log.e("theo doi"," "+title+" "+date+" "+i);
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
                Toast.makeText(context , "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();

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
        String name;

    public searc(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}

}
