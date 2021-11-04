package com.example.crytocurrency_ltdt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class fgment_news extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<News> newsArrayList;
    private News_adapter news_adapter;
    private View tabLayout;

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
        LinearLayoutManager llm =new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        getnews();
        news_adapter=new News_adapter(newsArrayList,getContext());
        recyclerView.setAdapter(news_adapter);
        tabLayout=view.findViewById(R.id.stl_main);
    }

    public void getnews(){
        String url="https://cryptopanic.com/api/v1/posts/?auth_token=f64c65903be4071cde0759ad80e39a43be78e94d&public=true&page=2";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
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
    }
}