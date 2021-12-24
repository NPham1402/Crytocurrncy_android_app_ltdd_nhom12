package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class button_openintroduction extends AppCompatActivity {
 private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_openintroduction);
        cardView=findViewById(R.id.cv_button_introduction);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sniper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("intrudution",false);
                editor.commit();
                Intent intent = new Intent(button_openintroduction.this,introduction.class);
                startActivity(intent);
                finish();
            }
        });
    }
}