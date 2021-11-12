package com.example.crytocurrency_ltdt;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class detail extends AppCompatActivity implements OnChartValueSelectedListener  {
    TextView sysmbol,name,rank,price;
    WebView description;
    WebView icon;
    String uuid;
    String name_item,description_iteml,url2,price_item,ranking,sysmbol_item;
    ArrayList<pricehitory> sparkline;
    private CombinedChart mChart;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        savedInstanceState=getIntent().getExtras();
        mChart = (CombinedChart) findViewById(R.id.linechart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        sparkline=new ArrayList<>();
        sysmbol=findViewById(R.id.detail_symbol);
        name=findViewById(R.id.detail_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        description=findViewById(R.id.detail_description);
        rank=findViewById(R.id.detail_rank);
        price=findViewById(R.id.detail_price);
        icon=findViewById(R.id.icon);
        uuid= savedInstanceState.getString("uuid");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        getcrypto_history(uuid, "1y", new VolleyCallBack() {
            @Override
            public void onSuccess() {
                YAxis rightAxis = mChart.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setAxisMinimum(0f);
                YAxis leftAxis = mChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                List <String> timestamp =new ArrayList<>();
                Float [] price = new Float[sparkline.size()];
                for (int i = 0; i < sparkline.size(); i++){
                    price[i]=sparkline.get(i).getPrice();
                    timestamp.add(sparkline.get(i).getTimestamp()+"");
                }
                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return timestamp.get((int) value % timestamp.size());
                    }
                });

                CombinedData data = new CombinedData();
                LineData lineDatas = new LineData();
                lineDatas.addDataSet((ILineDataSet) dataChart(price,uuid));

                data.setData(lineDatas);
                xAxis.setAxisMinimum(data.getXMin()-0.25f);
                xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                xAxis.setGranularity(data.getXMax()/timestamp.size());
                mChart.setData(data);
                mChart.invalidate();


            }
        });
        getcrypto(uuid, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                icon.loadDataWithBaseURL(null, "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Document</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <img src=\""+url2+"\">\n" +
                        "</body>\n" +
                        "</html>", "html/css", "utf-8", null);
                sysmbol.setText(sysmbol_item);
                getSupportActionBar().setTitle(""+sysmbol_item);
                name.setText(name_item);
                String encodedHtml = Base64.encodeToString(description_iteml.getBytes(),
                        Base64.NO_PADDING);
                description.loadData(encodedHtml,"text/html","base64");
                price.setText(price_item);
                rank.setText(ranking);
            }
        });

    }
    private static DataSet dataChart(Float [] data,String uuid) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, uuid);
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    public interface VolleyCallBack {
        void onSuccess();
    }
    public void getcrypto_history(String uuid,String date,final VolleyCallBack callBack){
        String url = "https://api.coinranking.com/v2/coin/" + uuid+"/history??timePeriod="+date;
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectReques= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("data");
                    JSONArray dataobject=object.getJSONArray("history");
                    for (int i=0; i<dataobject.length(); i++){
                        JSONObject price_history=dataobject.getJSONObject(i);
                        float price =Float.parseFloat(price_history.getString("price"));
                        Integer timrstamp= Integer.parseInt(price_history.getString("timestamp"));
                        sparkline.add(new pricehitory(price,timrstamp));

                        }
                    callBack.onSuccess();

                } catch (JSONException e) {
                    Log.e("volley",e+"");
                    Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley",error+"");
                Toast.makeText(getBaseContext(),error.toString()+" ",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token","coinranking4dca18b4fc6f95ee35cd4f07e36fb500f0c6aa66e1972d1f");
                return  headers;
            }

        };
        requestQueue.add(jsonObjectReques);
    }
    public void getcrypto(String a,final VolleyCallBack callBack){
        String url = "https://api.coinranking.com/v2/coin/" + a+"?timePeriod=1y";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectReques= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("data");
                    JSONObject dataobject=object.getJSONObject("coin");

                        sysmbol_item=dataobject.getString("symbol");

                         name_item=dataobject.getString("name");
                        description_iteml=dataobject.getString("description");
                     url2= dataobject.getString("iconUrl");
                        JSONArray sparkline_item=dataobject.getJSONArray("sparkline");
                         price_item=dataobject.getString("price");
                        ranking=dataobject.getString("rank");

                        callBack.onSuccess();

                } catch (JSONException e) {
                    Log.e("volley",e+"");
                    Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley",error+"");
                Toast.makeText(getBaseContext(),error.toString()+" ",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token","coinranking4dca18b4fc6f95ee35cd4f07e36fb500f0c6aa66e1972d1f");
                return  headers;
            }

        };
        requestQueue.add(jsonObjectReques);
    }
}
class pricehitory{
    Float price;

    public pricehitory( Float price, int timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    Integer timestamp;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}