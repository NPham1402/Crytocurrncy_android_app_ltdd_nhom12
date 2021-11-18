package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class fgment_cryto extends Fragment {
    TabLayout tabs;
    Tablayoutadater tabLayout;
    ViewPager2 views;
    View view_item;
    Toolbar tb;
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fgment_cryto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        Context context = Util.changeLang(getActivity().getBaseContext(), lang_code);

        tabs=view.findViewById(R.id.tab_layout_cryto);
        views=view.findViewById(R.id.Vp_refresh_layout);
        tabLayout= new Tablayoutadater(getChildFragmentManager(),getLifecycle());
        tabLayout.addFragment(new Item_cryto_like());
        tabLayout.addFragment(new item_cryto_all());
        views.setPageTransformer(new ZoomOutPageTransformer());
        views.setAdapter(tabLayout);
        new TabLayoutMediator(tabs, views,
                (tab, position) -> {
            if (position==0)
            {
                tab.setText(R.string.my_coin_like);
            }
            else {
                tab.setText(R.string.All_coin);
            }
                }
        ).attach();
        view_item=view.findViewById(R.id.total_holdings);
        view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),search.class);
                startActivity(intent);
            }
        });
//        tabs.addTab(tabs.newTab().setText("All"));
//        tabs.addTab(tabs.newTab().setText("Like"));

    }
}