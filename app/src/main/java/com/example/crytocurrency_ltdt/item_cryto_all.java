package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
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
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_cryto_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Context context = Util.changeLang(getActivity().getBaseContext(), lang_code ,f );
        SharedPreferences sharedPreferences1 = context.getSharedPreferences("sniper", Context.MODE_PRIVATE);
        recyclerView =view.findViewById(R.id.crypto_all);
        crytoArrayList =new ArrayList<>();
        int a=sharedPreferences1.getInt("snipervalues",2);
        Toast.makeText(getContext(),""+a,Toast.LENGTH_SHORT).show();
        getcrypto(a);
        swipe =view.findViewById(R.id.sf_refresh_layout1);
        swipe.setOnRefreshListener(this);
        LinearLayoutManager llm =new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Log.e("gvzxfh",""+crytoArrayList.size());
        adapter=new Cryto_adapter(crytoArrayList,getContext());
        recyclerView.setAdapter(adapter);
        spinner= view.findViewById(R.id.Spinner_sort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(a);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("sniper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("snipervalues",position);
                editor.commit();
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getcrypto(int position) {
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
                        double lastPrice = 0;
                        for (int j=0; i<data_sparkline.length()-1; j++){
                            if (data_sparkline.getString(j)=="null")
                            {
                                continue;
                            }
                            else{
                                Log.e("taf",data_sparkline.getString(j));
                                lastPrice=Double.parseDouble(data_sparkline.get(j).toString());
                                break;
                            }
                        }
                            double newPrice=Double.parseDouble(data_sparkline.get(data_sparkline.length()-1).toString());
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
        Toast.makeText(getContext(), R.string.Refresh, Toast.LENGTH_SHORT).show();
        crytoArrayList.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sniper", Context.MODE_PRIVATE);
                int a=sharedPreferences.getInt("snipervalues",0);
                getcrypto(a);
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