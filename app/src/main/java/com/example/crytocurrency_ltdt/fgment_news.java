package com.example.crytocurrency_ltdt;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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


public class fgment_news extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    Util util;
    private ArrayList<News> newsArrayList;
    private News_adapter news_adapter;
    private View tabLayout;
    SwipeRefreshLayout swipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fgment_news, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcv_news);
        newsArrayList=new ArrayList<>();
        swipe =view.findViewById(R.id.sf_refresh_layout2);
        swipe.setOnRefreshListener(this);
        util=new Util(getContext());
        LinearLayoutManager llm =new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        getnews_api();
        news_adapter=new News_adapter(newsArrayList,getContext());
        recyclerView.setAdapter(news_adapter);
        tabLayout=view.findViewById(R.id.stl_main);

    }
    public    void getnews_api(){
        String url="https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
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
                    Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext() , "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
//        String url2="https://news.google.com/rss/topics/CAAqJAgKIh5DQkFTRUFvS0wyMHZNSFp3YWpSZlloSUNaVzRvQUFQAQ?hl=en-US&gl=US&ceid=US%3Aen";
//
//        RequestQueue requestQueue2= Volley.newRequestQueue(getContext());
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try{
//                    XmlPullParserFactory xml=XmlPullParserFactory.newInstance();
//                    XmlPullParser paeser=xml.newPullParser();
//                    paeser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
//                    paeser.setFeature(response,false);
//                    int event=paeser.getEventType();
//                    while(event!=XmlPullParser.END_DOCUMENT){
//                        String eltname=null;
//                        switch (event){
//                            case XmlPullParser.START_TAG:
//                                eltname=paeser.getName();
//                                if ("channel".equals(eltname)){
//                                    News news=new News();
//                                    newsArrayList.add(news);
//                                }
//                                else if("".equals(eltname))
//
//
//                        }
//                    }
//                   news_adapter.notifyDataSetChanged();
//                }
//                catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) ;
//        requestQueue2.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
        newsArrayList.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                news_adapter.clear();
                  getnews_api();
                news_adapter.add(newsArrayList);
                swipe.setRefreshing(false);

            }
        }, 2000);
    }
}