package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cryto_adapter extends RecyclerView.Adapter<Cryto_adapter.ViewHolder> {
    private ArrayList<Cryto> crytos;
    private Context context;
    Util util;
    public void add(ArrayList<Cryto> crytos)
    {
    this.crytos=crytos;
    }
    public Cryto_adapter(ArrayList<Cryto> crytos, Context context) {
        this.crytos = crytos;
        this.context = context;
        util=new Util(context);
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
    holder.totalValue.setText( ( ""+(double) Math.round(  cryto.getPrice() * 100) / 100 ) );
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
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);
        //double kq = (double) Math.round(((a-b) * 10000)) / 10000;
        double kq2 = b - a ;
        double kq3 = (double) Math.round(  b * 100) / 100 ;
        double tron =  (double) Math.round(  ( ( (b-a)/a )*100 ) * 100) / 100   ;
        if ((a-b)>0)
        {
            holder.changePercent2.setTextColor(context.getColor(R.color.error_red));
            holder.changePercent.setTextColor(context.getColor(R.color.error_red));
            //holder.changePercent.setText( ( tron  + "%" ) );

        }
        else {
            holder.changePercent2.setTextColor(context.getColor(R.color.positive_green));
            holder.changePercent.setTextColor(context.getColor(R.color.positive_green));
            //holder.changePercent.setText((  tron + "%"));
        }

        /*Dấu phẩy version*/
        /*holder.changePercent2.setText( ( df.format(b) +"" ));*/

        holder.changePercent2.setText((kq3 + "") );
        holder.changePercent.setText( ( tron  + "%" ) );
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,cryto.getSymbol(),Toast.LENGTH_SHORT).show();
            }
        });
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
        private TextView ticker,namem,totalValue ,changePercent, changePercent2,ranking;
        private LinearLayout stockContainer;
        private ImageView imageView;
        public ViewHolder(@NonNull View view) {
            super(view);
            ticker=view.findViewById(R.id.ticker);
            namem=view.findViewById(R.id.name);
            totalValue=view.findViewById(R.id.totalValue);
            changePercent=view.findViewById(R.id.changePercent);
            changePercent2=view.findViewById(R.id.changePercent2);
            ranking=view.findViewById(R.id.ranking);
            stockContainer=view.findViewById(R.id.stockContainer);
            imageView=view.findViewById(R.id.more_menu);
        }
    }
}
