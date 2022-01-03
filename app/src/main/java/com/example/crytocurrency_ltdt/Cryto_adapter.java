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
    ArrayList<String> list;
    //  Util util;

    public void add(ArrayList<Cryto> crytos) {
        this.crytos = crytos;
    }

    public Cryto_adapter(ArrayList<Cryto> crytos, Context context) {
        this.crytos = crytos;
        this.context = context;
        //    util = new Util(context);
    }

    @Override
    public Cryto_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iten_stock, parent, false);
        return new Cryto_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cryto_adapter.ViewHolder holder, int position) {
        Cryto cryto = crytos.get(position);
        holder.ticker.setText(cryto.getSymbol());
        holder.namem.setText(cryto.getName());
        holder.ranking.setText(cryto.getRank());
        //holder.stockContainer.setBackgroundColor(Color.parseColor(cryto.getColor()));
        double a = cryto.getLastPrice();
        double b = cryto.getNewPrice();

        holder.stockContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detail.class);
                intent.putExtra("uuid", cryto.getUuid());
                context.startActivity(intent);
            }
        });
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        //double kq = (double) Math.round(((a-b) * 10000)) / 10000;
        double kq2 = b - a;
        double tron = (double) Math.round((((b - a) / a) * 100) * 100) / 100;
        if ((a - b) > 0) {
            holder.changePercent2.setTextColor(context.getColor(R.color.error_red));
            holder.changePercent.setTextColor(context.getColor(R.color.error_red));
            //holder.changePercent.setText( ( tron  + "%" ) );

        } else {
            holder.changePercent2.setTextColor(context.getColor(R.color.positive_green));
            holder.changePercent.setTextColor(context.getColor(R.color.positive_green));
            //holder.changePercent.setText((  tron + "%"));
        }

        holder.changePercent2.setText((df.format(b) + ""));
        holder.changePercent.setText((tron + "%"));
        if (preconfig.read(context)!=null) {
            if(cryto.getStatus()==true) {
                Log.e(cryto.getName(), cryto.getUuid());
                holder.imageView.setImageResource(R.drawable.ic_baseline_remove_circle_24);
            }
            else
            {
                Log.e("false",cryto.getName());
                holder.imageView.setImageResource(R.drawable.ic_baseline_add_circle_24);
            }
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preconfig.read(context) == null) {
                    ArrayList<String> uuids = new ArrayList<>();
                    uuids.add("Nguyen");
                    preconfig.write(context, uuids);

                }
                if (!preconfig.read(context).contains(cryto.getUuid())) {
                    holder.imageView.setImageResource(R.drawable.ic_baseline_remove_circle_24);
                    ArrayList<String> uuid = new ArrayList<>();
                    uuid.addAll(preconfig.read(context));
                    uuid.add(cryto.getUuid());
                    preconfig.write(context, uuid);
                    preconfig.read(context);
                } else {
                    holder.imageView.setImageResource(R.drawable.ic_baseline_add_circle_24);
                    preconfig.remove(context,cryto.getUuid());

                }

            }
        });
    }

    public void clear() {
        crytos.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return crytos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ticker, namem, totalValue, changePercent, changePercent2, ranking;
        private LinearLayout stockContainer;
        private ImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);

            ticker = view.findViewById(R.id.ticker);
            namem = view.findViewById(R.id.name);
            changePercent = view.findViewById(R.id.changePercent);
            changePercent2 = view.findViewById(R.id.changePercent2);
            ranking = view.findViewById(R.id.ranking);
            stockContainer = view.findViewById(R.id.stockContainer);
            imageView = view.findViewById(R.id.more_menu);
        }
    }
}
