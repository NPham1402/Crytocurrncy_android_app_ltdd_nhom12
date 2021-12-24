package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import me.relex.circleindicator.CircleIndicator3;

public class introduction extends AppCompatActivity {
    private ViewPager2 viewpager2;
    private LinearLayout next;
    private Handler mhandler= new Handler();
    private viewpageradapter_introdution viewpageradapter;
    private TextView skip;
    private CircleIndicator3 circleindicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences1 = getSharedPreferences("sniper", Context.MODE_PRIVATE);
        boolean a=sharedPreferences1.getBoolean("intrudution",false);
        if(a==true){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_introduction);
        viewpager2 = findViewById(R.id.vp_introduction);
        circleindicator = findViewById(R.id.circle);
        skip = findViewById(R.id.tv_skip);
        viewpageradapter = new viewpageradapter_introdution(getSupportFragmentManager(), getLifecycle());
        viewpager2.setAdapter(viewpageradapter);
        circleindicator.setViewPager(viewpager2);
        viewpager2.setPageTransformer(new ZoomOutPageTransformer());
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager2.setCurrentItem(3);
            }
        });
      callbacks.run();
      viewpager2.setCurrentItem(0);
    }
    private Runnable callbacks = new Runnable() {
        @Override
        public void run() {
            if (viewpager2.getCurrentItem()<=3){
                viewpager2.setCurrentItem(viewpager2.getCurrentItem() + 1,true);
            }
            mhandler.postDelayed(this,6000);

        }
    };
}