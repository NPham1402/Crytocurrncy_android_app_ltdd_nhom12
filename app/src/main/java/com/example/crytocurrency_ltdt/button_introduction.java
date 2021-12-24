package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.ybq.android.spinkit.SpinKitView;

public class button_introduction{
    CardView cardView;
    TextView textView;
    SpinKitView spinKitView;
    ConstraintLayout constraintLayout;
    Animation fade_in;

    public button_introduction(Context context, View view) {
        fade_in= AnimationUtils.loadAnimation(context,R.anim.animation);
        cardView=view.findViewById(R.id.cv_button_login);
        textView=view.findViewById(R.id.txt_login);
        spinKitView=view.findViewById(R.id.prgbr_login_button);
        constraintLayout=view.findViewById(R.id.ct_login_button);
    }
    
    void press(){
        textView.setAnimation(fade_in);
        textView.setText("Loading");
        spinKitView.setAnimation(fade_in);
        spinKitView.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        textView.setTextColor(cardView.getResources().getColor(R.color.black));
        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.Green));
    }
    void done(){
        textView.setAnimation(fade_in);
        textView.setText("Done");
        spinKitView.setAnimation(fade_in);
        spinKitView.setVisibility(View.GONE);
        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.DarkGray));
    }
    String gettext(){
        return textView.getText().toString();
    }
}
