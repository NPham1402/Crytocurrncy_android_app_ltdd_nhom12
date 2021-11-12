package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class Util {
    Context context;
    ArrayList<Cryto> crytoArrayList;
    ArrayList<String> arrayticker;
    ArrayList<News> newsArrayList;
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
    public  ArrayList<String> getticker(){
    return  arrayticker;
    }
    public Util(Context context) {
        this.context = context;
        arrayticker=new ArrayList<String>();
        crytoArrayList=new ArrayList<Cryto>( );
        newsArrayList=new ArrayList<News>();
    }
    public ArrayList<Cryto> getcrypto(){
        crytoArrayList.clear();
        getcrypto_api();
        return crytoArrayList;
    }
    public ArrayList<News> getnews(){
        newsArrayList.clear();
      //  getnews_api();
        Toast.makeText(context,""+newsArrayList.size(), Toast.LENGTH_SHORT).show();
        return newsArrayList;
    }
    public void  getcrypto_api(){
        String url=" https://api.coinranking.com/v2/coins?timePeriod=3h";
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectReques= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("data");
                    JSONArray data=object.getJSONArray("coins");
                    for (int i=0; i<data.length(); i++) {
                        JSONObject dataobject =data.getJSONObject(i);
                        String ticker = dataobject.getString("symbol");
                        String name=dataobject.getString("name");
                        String color=dataobject.getString("color");
                        Double price=dataobject.getDouble("price");
                        String rank=dataobject.getString("rank");
                        JSONArray data_sparkline=dataobject.getJSONArray("sparkline");
                        double lastPrice=Double.parseDouble(data_sparkline.get(0).toString()) ;
                        double newPrice=Double.parseDouble(data_sparkline.get(data_sparkline.length()-1).toString());
                        Log.e("tICVKER",ticker+"-"+ newPrice);
                      //  crytoArrayList.add(new Cryto(ticker,name,color,price,rank,lastPrice,newPrice));
                        arrayticker.add(ticker);
                    }

                } catch (JSONException e) {
                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString()+" ",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token","coinranking4dca18b4fc6f95ee35cd4f07e36fb500f0c6aa66e1972d1f");
                return  headers;
            }
        };       requestQueue.add(jsonObjectReques);
       Log.d("asbsh",crytoArrayList.size()+"");
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


}
