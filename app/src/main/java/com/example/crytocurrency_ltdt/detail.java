package com.example.crytocurrency_ltdt;



import static com.google.android.material.internal.ContextUtils.getActivity;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class detail extends AppCompatActivity implements OnChartValueSelectedListener  {
    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));


    private SeekBar seekBar;
    TextView sysmbol,name,rank,price;
    Context context;
    WebView description;
    ImageView icon;
    String uuid, date;
    String name_item,description_iteml,url2,price_item,ranking,sysmbol_item;
    ArrayList<pricehitory> sparkline;
    Uri myUri; //= Uri.parse(url2);
    /*private CombinedChart mChart;*/


    public class MyXValue extends ValueFormatter{

        private ArrayList<String> xValue  ;
        public MyXValue(ArrayList<String> xValue) {
            this.xValue = xValue;
        }  /* <----- 2 dòng thừa , không rõ mục ích*/

        @Override
        public String getFormattedValue(float value) {
            /*return xValue.get((int)value);*/
            return EpochToTime(Float.parseFloat(df.format(value)) ) ;
        }
    }
    LineChart mChart;

    public String EpochToDate(float i) {
        long fbt = (long) i;

        /*return  dateTime.format(formatter);*/
        /*String fromat =  new DateTime( fbt * 1000, DateTimeZone.UTC ).toString();*/
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date  = formatter.format(new Date(fbt));
        return date;
    }

    public String EpochToTime(float i) {
        long fbt = (long) i;
        /*ZonedDateTime dateTime = Instant.ofEpochMilli(fbt).atZone(ZoneId.of("Asia/Jakarta"));*/
        /*return  dateTime.format(formatter);*/
        /*String fromat =  new DateTime( fbt * 1000, DateTimeZone.UTC ).toString();*/
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm ");
        DateTime dt =  new DateTime( fbt * 1000, DateTimeZone.UTC );
        /*String time  = formatter.format(new Date(fbt));*/
        String minute = String.valueOf(dt.getMinuteOfHour());
        String hours =   String.valueOf(dt.getHourOfDay());
        String day=   String.valueOf(dt.getDayOfWeek());
        String week=   String.valueOf(dt.getWeekOfWeekyear());
        String month=   String.valueOf(dt.getMonthOfYear());
        String year=   String.valueOf(dt.getYear());
        if(seekBar.getProgress() == 0)
            return new String(hours +":" + minute) ;
        else if (seekBar.getProgress() == 1)
            return day + "week" +week;
        else if (seekBar.getProgress() == 2)
            return week +"month" +month;
        else
            return month+"year" +year;
    }
    public int EpochToTest(float i) {
  long fbt = (long) i;
        DateTime dt =  new DateTime( fbt * 1000, DateTimeZone.UTC );
        /*SimpleDateFormat formatter = new SimpleDateFormat("mm");
        String time  = formatter.format(new Date(fbt));*/
        /*Log.e("minute of hour ", (String.valueOf(dt)));
        return dt.getMinuteOfHour();*/
        if(seekBar.getProgress() == 0)
            return dt.getHourOfDay();
        else if (seekBar.getProgress() == 1){
            /*7d*/

            return dt.getDayOfWeek();}
        else if (seekBar.getProgress() == 2) /*3M*/
            return dt.getWeekOfWeekyear();
        else                                  /*1Y*/
            return dt.getMonthOfYear();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void readsvg(String url){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = this;
        date="24h";
        savedInstanceState=getIntent().getExtras();
        /*mChart = (CombinedChart) findViewById(R.id.linechart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);*/

        df.setMaximumFractionDigits(340);
        mChart =  findViewById(R.id.linechart);
        seekBar =  findViewById(R.id.sb_chart);
        seekBar.setMax(3);       /*<-- mức của seekbar*/
        seekBar.setProgress(0); /*<-- giá trị của seekbar*/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                mChart.clear();
                Log.i("progress :", String.valueOf(progress));
                if(progress == 0)
                    date = "24h";
                else if (progress == 1)
                    date = "7d";
                else if (progress == 2)
                    date = "3m";
                else if (progress == 3)
                    date = "1y";
                getcrypto_history(uuid, date, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                        /*List <String> timestamp =new ArrayList<>();*/
                        mChart.setDragEnabled(true);
                        mChart.setScaleEnabled(false);
                        Float [] timestamp = new Float[sparkline.size()];
                        Float [] price = new Float[sparkline.size()];
                        for (int i = 0; i < sparkline.size(); i++){
                            price[i]=sparkline.get(i).getPrice();
                            timestamp[i]=sparkline.get(i).getTimestamp();
                            /*timestamp.add(sparkline.get(i).getTimestamp()+"");*/
                        }

                        ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
                        ArrayList<String> xLabel = new ArrayList<String>();
                        int Moh , trung;
                        trung = 0;
                        Moh = EpochToTest(Float.parseFloat(df.format(timestamp[0])));
                        for (int i = 0; i < 100; i++) {
                            int min  = EpochToTest(Float.parseFloat(df.format(timestamp[i])));
                            if( ( Moh == EpochToTest(Float.parseFloat(df.format(timestamp[0])))  || min != Moh ) && min > trung ) {
                                dataVal1.add(new Entry(timestamp[i], price[i]));
                        /*Log.e("timestamp ", (timestamp[i].toString()));
                        Log.e("timestamp format ", (df.format(timestamp[i])));
                        Log.e("date ", (EpochToDate(Float.parseFloat(df.format(timestamp[i])))) + "");*/
                                xLabel.add((EpochToTime(Float.parseFloat(df.format(timestamp[i])))));
                                Moh = min;
                                trung = min;
                            }
                        }
                        LineDataSet linedataset1 = new LineDataSet( dataVal1,getResources().getString(R.string.PriceChart));
                        ArrayList<ILineDataSet> DataSets = new ArrayList<>();
                        DataSets.add(linedataset1);
                        LineData lineDatas = new LineData(DataSets);

                        YAxis leftAxis = mChart.getAxisLeft();
                        mChart.getAxisRight().setEnabled(false);

                        mChart.setData(lineDatas);
                        mChart.invalidate();
                        XAxis xAxis =  mChart.getXAxis();
                        xAxis.setDrawGridLinesBehindData(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new MyXValue(xLabel) );
                        xAxis.setTextSize(2f);



                        linedataset1.setColor(Color.BLUE);
                        linedataset1.setCircleColor(Color.rgb(240, 238, 70));
                        // set.setFillColor(Color.rgb(240, 238, 70));
                        linedataset1.setDrawValues(true);
                        linedataset1.setValueTextColor(Color.rgb(240, 238, 70));
                        linedataset1.setHighLightColor(Color.MAGENTA);
                        linedataset1.setAxisDependency(YAxis.AxisDependency.LEFT);

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
        getcrypto_history(uuid, date, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                /*YAxis rightAxis = mChart.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setAxisMinimum(0.1f);
                YAxis leftAxis = mChart.getAxisLeft();
                leftAxis.setDrawGridLines(true);
                leftAxis.setAxisMinimum(0.1f);*/
                /*List <String> timestamp =new ArrayList<>();*/
                mChart.setDragEnabled(true);
                mChart.setScaleEnabled(false);
                Float [] timestamp = new Float[sparkline.size()];
                Float [] price = new Float[sparkline.size()];
                for (int i = 0; i < sparkline.size(); i++){
                    price[i]=sparkline.get(i).getPrice();
                    timestamp[i]=sparkline.get(i).getTimestamp();
                    /*timestamp.add(sparkline.get(i).getTimestamp()+"");*/
                }

                ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
                ArrayList<String> xLabel = new ArrayList<String>();
                int Moh , trung;
                trung = 0;
                Moh = EpochToTest(Float.parseFloat(df.format(timestamp[0])));
                for (int i = 0; i < sparkline.size(); i++) {
                    int min  = EpochToTest(Float.parseFloat(df.format(timestamp[i])));
                    if( ( Moh == EpochToTest(Float.parseFloat(df.format(timestamp[0])))  || min != Moh ) && min > trung ) {
                        dataVal1.add(new Entry(timestamp[i], price[i]));
                        /*Log.e("timestamp ", (timestamp[i].toString()));
                        Log.e("timestamp format ", (df.format(timestamp[i])));
                        Log.e("date ", (EpochToDate(Float.parseFloat(df.format(timestamp[i])))) + "");*/
                        /*KHÔNG XÀI ------>*/       xLabel.add((EpochToTime(Float.parseFloat(df.format(timestamp[i])))));
                        Moh = min;
                        trung = min;
                    }
                }
                LineDataSet linedataset1 = new LineDataSet( dataVal1,getResources().getString(R.string.PriceChart));
                ArrayList<ILineDataSet> DataSets = new ArrayList<>();
                DataSets.add(linedataset1);
                LineData lineDatas = new LineData(DataSets);

                YAxis leftAxis = mChart.getAxisLeft();
                mChart.getAxisRight().setEnabled(false);

                mChart.setData(lineDatas);
                mChart.invalidate();
                XAxis xAxis =  mChart.getXAxis();
                xAxis.setDrawGridLinesBehindData(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new MyXValue(xLabel) );
                xAxis.setTextSize(2f);



                linedataset1.setColor(Color.BLUE);
                linedataset1.setCircleColor(Color.rgb(240, 238, 70));
                // set.setFillColor(Color.rgb(240, 238, 70));
                linedataset1.setDrawValues(true);
                linedataset1.setValueTextColor(Color.rgb(240, 238, 70));
                linedataset1.setHighLightColor(Color.MAGENTA);
                linedataset1.setAxisDependency(YAxis.AxisDependency.LEFT);

                /*XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        int value2 = Math.round(value);
                        DateTimeZone timeZone = DateTimeZone.forID( "Asia/Jakarta" );
                        DateTimeFormatter formatter = DateTimeFormatter
                                .ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.US);
                        long fbt = (long) timestamp[value2];
                        ZonedDateTime dateTime = Instant.ofEpochMilli(fbt).atZone(ZoneId.of("Asia/Jakarta"));
                        return timestamp.get((int) value % timestamp.size());
                        return  dateTime.format(formatter);
                    }
                });*/

                /*CombinedData data = new CombinedData();*/
               /* LineData data = new LineData();
                LineData lineDatas = new LineData();
                lineDatas.addDataSet((ILineDataSet) dataChart(price,uuid));

                data.setData(lineDatas);
                xAxis.setAxisMinimum(data.getXMin()-0.25f);
                xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                xAxis.setGranularity(data.getXMax()/timestamp.size());
                mChart.setData(data);
                mChart.invalidate();*/


            }
        });
        getcrypto(uuid, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                sysmbol.setText(sysmbol_item);
                DecimalFormat df = new DecimalFormat("0.00");
                getSupportActionBar().setTitle(""+sysmbol_item);
                name.setText(name_item);
                String encodedHtml = Base64.encodeToString(description_iteml.getBytes(),
                        Base64.NO_PADDING);
                description.loadData(encodedHtml,"text/html","base64");
                price.setText(df.format(Double.parseDouble(price_item)));
                rank.setText(ranking);
                GlideToVectorYou.justLoadImage(detail.this, myUri, icon);
            }
        });
        /*Glide.with(this).load(url2).into(icon);*/

    }
    /*private ArrayList<Entry>dataValue1(){
        ArrayList<Entry> dataVal1 = new ArrayList<Entry>();
        for (int i = 0; i < sparkline.size(); i++) {
            price[i] = sparkline.get(i).getPrice();
            timestamp.add(sparkline.get(i).getTimestamp() + "");

            dataVal1.add(new Entry())
        }
        return dataVal1;
    }*/

    private static DataSet dataChart(Float [] data,String uuid) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 12; index++) {
            entries.add(new Entry(index, data[index]));
        }


        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.BLUE);
        set.setLineWidth(3.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleSize(5f);
        // set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));
        set.setHighLightColor(Color.MAGENTA);
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
        String url = "https://api.coinranking.com/v2/coin/"+uuid+"/history?timePeriod="+date;
        /*Log.e("nguyen",url);*/
        sparkline.clear();
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

                        /*Integer*/ Float timrstamp= Float.parseFloat(price_history.getString("timestamp"));
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
                    myUri = Uri.parse(dataobject.getString("iconUrl"));
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

    public pricehitory( Float price, float timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    Float timestamp;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }
}