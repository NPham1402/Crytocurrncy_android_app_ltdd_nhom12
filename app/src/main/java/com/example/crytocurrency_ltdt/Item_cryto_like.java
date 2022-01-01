package com.example.crytocurrency_ltdt;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Item_cryto_like extends Fragment implements adapter_item_like.MyInterface {
    ArrayList<Cryto> crytoArrayList;
    adapter_item_like adapter;

    SwipeRefreshLayout swipeRefreshLayout;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_cryto_like, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.refresh2);
        gridView = view.findViewById(R.id.list);
        crytoArrayList = new ArrayList<>();
        getcrypto(2);
        adapter = new adapter_item_like(getContext(), crytoArrayList);
        adapter.set(this);
        gridView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                crytoArrayList.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getcrypto(2);
                        adapter.addlist(crytoArrayList);
                        adapter.notifyDataSetChanged();
                        gridView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    public void getcrypto(int position) {
        String url = "https://api.coinranking.com/v2/coins?";
        if (preconfig.read(getContext()) != null) {
            for (int i = 0; i < preconfig.read(getContext()).size(); i++) {
                if (i == preconfig.read(getContext()).size() - 1) {
                    url = url + "uuids[]=" + preconfig.read(getContext()).get(i);
                } else {
                    url = url + "uuids[]=" + preconfig.read(getContext()).get(i) + "&";
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectReques = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject object = response.getJSONObject("data");
                        JSONArray data = object.getJSONArray("coins");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject dataobject = data.getJSONObject(i);
                            String uuid = dataobject.getString("uuid");
                            String ticker = dataobject.getString("symbol");
                            String name = dataobject.getString("name");
                            String color = dataobject.getString("color");
                            Double price = dataobject.getDouble("price");
                            String rank = dataobject.getString("rank");
                            JSONArray data_sparkline = dataobject.getJSONArray("sparkline");
                            double lastPrice = 0;
                            for (int j = 0; i < data_sparkline.length() - 1; j++) {
                                if (data_sparkline.getString(j) == "null") {
                                    continue;
                                } else {
                                    lastPrice = Double.parseDouble(data_sparkline.get(j).toString());
                                    break;
                                }
                            }
                            Boolean status = false;
                            double newPrice = Double.parseDouble(data_sparkline.get(data_sparkline.length() - 1).toString());
                            crytoArrayList.add(new Cryto(uuid, ticker, name, color, price, rank, lastPrice, newPrice, status));
                            if (position <= 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    crytoArrayList.sort(((o1, o2) -> o1.getName().compareTo(o2.getName())));
                                }
                                if (position == 1) {
                                    Collections.reverse(crytoArrayList);
                                }

                            }
                            if (position == 3) {
                                Collections.reverse(crytoArrayList);
                            }


                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString() + " ", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("x-access-token", "coinranking4dca18b4fc6f95ee35cd4f07e36fb500f0c6aa66e1972d1f");

                    return headers;
                }

            };
            requestQueue.add(jsonObjectReques);
        }
    }

    @Override
    public void foo() {
        crytoArrayList.clear();
        getcrypto(2);
        adapter.addlist(crytoArrayList);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }
}