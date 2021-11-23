package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class search extends AppCompatActivity {
    SearchView search;
    serch_adapter Adapter;
    ArrayList<Util.searc> tickers;
    Util util;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.searchView);
        util =new Util(this);
        recyclerView=findViewById(R.id.searchView_recyclerview);
        tickers = new ArrayList<Util.searc>();
        getcrypto_api(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                Log.e("size1:",tickers.size()+"");
                LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                Adapter=new serch_adapter(tickers,getBaseContext());
                recyclerView.setAdapter(Adapter);
                search.setIconifiedByDefault(true);
                search.setFocusable(true);
                search.setIconified(false);
                search.requestFocusFromTouch();
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        search(newText);
                        return false;
                    }
                });
            }
        });

    }
    private void search(String textSearch){
        Log.e("textsearch:",textSearch);
        ArrayList<Util.searc> searchList = new ArrayList<>();
        if (searchList.size()>0){
            Adapter.clear();
        }
        for (Util.searc furniture:tickers){

            if (furniture.name.toLowerCase().contains(textSearch.toLowerCase())){
                searchList.add(furniture);
            }

        }
        Adapter.addlist(searchList);
        Adapter.notifyDataSetChanged();

    }
    public interface VolleyCallBack {
        void onSuccess();
    }
    public void   getcrypto_api(final VolleyCallBack callBack){
        String url=" https://api.coinranking.com/v2/coins?timePeriod=3h";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectReques= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("data");
                    JSONArray data=object.getJSONArray("coins");
                    for (int i=0; i<data.length(); i++) {
                        JSONObject dataobject =data.getJSONObject(i);
                        String uuid=dataobject.getString("uuid");
                        String ticker = dataobject.getString("name");
                        Log.e("size",tickers.size()+"");
                        //  crytoArrayList.add(new Cryto(ticker,name,color,price,rank,lastPrice,newPrice));
                        tickers.add(new Util.searc(uuid,ticker));
                    }
                    callBack.onSuccess();

                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString()+" ",Toast.LENGTH_SHORT).show();

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
    public void hideSoftKeyboard(View view){
        InputMethodManager imm
                =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}