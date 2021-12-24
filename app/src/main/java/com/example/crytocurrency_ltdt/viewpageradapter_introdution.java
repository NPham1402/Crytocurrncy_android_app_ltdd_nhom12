package com.example.crytocurrency_ltdt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpageradapter_introdution extends FragmentStateAdapter {

    public viewpageradapter_introdution(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:return new introduction1();
            case 1: return  new introduction2();
            case 2: return new introduction3();
            case 3: return new introduction4();
            default: return new introduction1();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
