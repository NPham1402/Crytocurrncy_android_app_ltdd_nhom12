package com.example.crytocurrency_ltdt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News_notification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title,decrition;
        ArrayList<News> newsArrayList=new ArrayList<News>();
        String url="https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2";
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data=response.getJSONArray("results");
                    for (int i=0;i<data.length();i++){
                        JSONObject dataobject=data.getJSONObject(i);
                        String domain=dataobject.getString("domain");
                        String title=dataobject.getString("title");
                        Log.e("lay",domain);
                        String date=dataobject.getString("published_at");
                        Log.e("theo doi"," "+title+" "+date+" "+i);
//             //            JSONArray currencies=dataobject.getJSONArray("currencies");
//                         String sysmbol="";
//
//                         for (int j=0;j<currencies.length();j++){
//                             JSONObject dataobject2=currencies.getJSONObject(j);
//
//                             sysmbol=dataobject2.getString("code");
//
//                         }

                        newsArrayList.add(new News(title,date,domain));
                    }
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
       title= newsArrayList.get(0).getTitle_post();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"news_cryto").setSmallIcon(R.drawable.ic_baseline_circle_notifications_24);
    }

    }

