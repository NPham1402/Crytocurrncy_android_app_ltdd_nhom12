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

public class item_cryto_all extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    ArrayList<Cryto> crytoArrayList;
    Cryto_adapter adapter;
    Util util;
    SwipeRefreshLayout swipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_cryto_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.crypto_all);
        crytoArrayList =new ArrayList<>();
        util=new Util(getContext());
        crytoArrayList=util.getcrypto();
        swipe =view.findViewById(R.id.sf_refresh_layout1);
        swipe.setOnRefreshListener(this);
        LinearLayoutManager llm =new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        getcrypto();
        Log.e("gvzxfh",""+crytoArrayList.size());
        adapter=new Cryto_adapter(crytoArrayList,getContext());
        recyclerView.setAdapter(adapter);

    }

    public void getcrypto(){
        String url=" https://api.coinranking.com/v2/coins?timePeriod=24h";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectReques= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("data");
                    JSONArray data=object.getJSONArray("coins");
                    for (int i=0; i<data.length(); i++) {
                        JSONObject dataobject =data.getJSONObject(i);
                        String uuid = dataobject.getString("uuid");
                        String ticker = dataobject.getString("symbol");
                        String name=dataobject.getString("name");
                        String color=dataobject.getString("color");
                        Double price=dataobject.getDouble("price");
                        String rank=dataobject.getString("rank");
                        JSONArray data_sparkline=dataobject.getJSONArray("sparkline");
                        double lastPrice=Double.parseDouble(data_sparkline.get(0).toString()) ;
                        double newPrice=Double.parseDouble(data_sparkline.get(data_sparkline.length()-1).toString());
                        Log.e("tICVKER",lastPrice+"-"+ newPrice);
                        crytoArrayList.add(new Cryto(uuid,ticker,name,color,price,rank,lastPrice,newPrice));

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString()+" ",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token","coinranking4dca18b4fc6f95ee35cd4f07e36fb500f0c6aa66e1972d1f");
                return  headers;
            }
        };       requestQueue.add(jsonObjectReques);
    }
    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_SHORT).show();
        crytoArrayList.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
               getcrypto();
                adapter.add(crytoArrayList);
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);

            }
        }, 2000);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}