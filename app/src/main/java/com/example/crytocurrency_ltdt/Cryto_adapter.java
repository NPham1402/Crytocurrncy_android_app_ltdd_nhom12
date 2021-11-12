package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Cryto_adapter extends RecyclerView.Adapter<Cryto_adapter.ViewHolder> {
    private ArrayList<Cryto> crytos;
    private Context context;
    public void add(ArrayList<Cryto> crytos)
    {
    this.crytos=crytos;
    }
    public Cryto_adapter(ArrayList<Cryto> crytos, Context context) {
        this.crytos = crytos;
        this.context = context;
    }

    @Override
    public Cryto_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.iten_stock,parent,false);
        return new Cryto_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cryto_adapter.ViewHolder holder, int position) {
    Cryto cryto=crytos.get(position);
    holder.ticker.setText(cryto.getSymbol());
    holder.namem.setText(cryto.getName());
    holder.totalValue.setText(""+cryto.getPrice());
    holder.ranking.setText(cryto.getRank());
    //holder.stockContainer.setBackgroundColor(Color.parseColor(cryto.getColor()));
        double a=cryto.getLastPrice();
        double b= cryto.getNewPrice();
        Log.e("test",(a)+"");
        Log.e("test",(b)+"");
        Log.e("test",(a-b)+"");
        holder.stockContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,detail.class);
                intent.putExtra("uuid",cryto.getUuid());
                context.startActivity(intent);
            }
        });
    if ((a-b)<0)
    {
        holder.changePercent.setTextColor(context.getColor(R.color.error_red));
    }
    else {
        holder.changePercent.setTextColor(context.getColor(R.color.positive_green));
    }
        holder.changePercent.setText((a-b)+"");
    }
    public void clear(){
        crytos.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return crytos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ticker,namem,totalValue ,changePercent,ranking;
        private LinearLayout stockContainer;
        public ViewHolder(@NonNull View view) {
            super(view);
            ticker=view.findViewById(R.id.ticker);
            namem=view.findViewById(R.id.name);
            totalValue=view.findViewById(R.id.totalValue);
            changePercent=view.findViewById(R.id.changePercent);
            ranking=view.findViewById(R.id.ranking);
            stockContainer=view.findViewById(R.id.stockContainer);
        }
    }
}
