package com.example.crytocurrency_ltdt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class activitive_screen_main extends AppCompatActivity {
    SpaceTabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitive_screen_main);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new fgment_news());
        fragmentList.add(new fgment_cryto()                                                                                                                                                                      );
        fragmentList.add(new fgment_setting());
        ViewPager viewPager = (ViewPager) findViewById(R.id.vtl_main);
        tabLayout = (SpaceTabLayout) findViewById(R.id.stl_main);
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        viewPager.setOnTouchListener(new traslate_hiden_scoller_view(getBaseContext(),tabLayout));


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}