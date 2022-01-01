package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;

public class fgment_cryto extends Fragment {
    TabLayout tabs;
    Tablayoutadater tabLayout;
    ViewPager2 views;
    View view_item;
    Toolbar tb;
    Spinner spinerSort;

    private void reduceDragSensitivity() {
        try {
            Field ff = ViewPager2.class.getDeclaredField("mRecyclerView") ;
            ff.setAccessible(true);
            RecyclerView recyclerView =  (RecyclerView) ff.get(views);
            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop") ;
            touchSlopField.setAccessible(true);
            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView,touchSlop*4);    /*<---- ĐỘ NHẠY CỦA VIEWPAGE*/
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fgment_cryto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*spinerSort = (Spinner)view.findViewById(R.id.spinner3);
        ArrayList<SpinnerItem> items = new ArrayList<SpinnerItem>();
        items.add(new SpinnerItem(MyApplication.getRes().getString(R.string.sort_name), false));
        items.add(new SpinnerItem(MyApplication.getRes().getString(R.string.sort_price), false));
        items.add(new SpinnerItem(MyApplication.getRes().getString(R.string.sort_percent), false));
        items.add(new SpinnerItem(MyApplication.getRes().getString(R.string.sort_as), true)); // Last item

        MySpinnerAdapter adapter = new MySpinnerAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerSort.setAdapter(adapter);
        spinerSort.setSelection(items.size() - 1);*/


        /*spinerSort = (Spinner)view.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.sort,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerSort.setAdapter(adapter);*/


        /*spinerSort.setSelection(adapter.getCount() - 1);
        spinerSort.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    adapter.remove(adapter.getItem(adapter.getCount() - 1));
                }
                return false;
            }
        });*/



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String lang_code= sharedPreferences.getString("Language", "vi");//load it from SharedPref
        float f = Float.parseFloat(sharedPreferences.getString("textsize", "1.0f"));
        Context context = Util.changeLang(getActivity().getBaseContext(), lang_code ,f);

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
        reduceDragSensitivity();
    }

    }

