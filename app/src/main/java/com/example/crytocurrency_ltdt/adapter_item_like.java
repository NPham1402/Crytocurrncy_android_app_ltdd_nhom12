package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapter_item_like extends BaseAdapter  implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    ArrayList<Cryto> crytos;
    private MyInterface listener;

    public adapter_item_like(Context context, ArrayList<Cryto> crytos) {
        this.context = context;
        this.crytos = crytos;

    }

    public void addlist(ArrayList<Cryto> list) {
        crytos = list;
    }

    @Override
    public int getCount() {
        return crytos.size();
    }

    @Override
    public Object getItem(int position) {
        notifyDataSetChanged();
        return crytos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.iten_stock, parent, false);
            view = new ViewHolder();
            view.ticker = convertView.findViewById(R.id.ticker);
            view.namem = convertView.findViewById(R.id.name);
            view.changePercent = convertView.findViewById(R.id.changePercent);
            view.changePercent2 = convertView.findViewById(R.id.changePercent2);
            view.ranking = convertView.findViewById(R.id.ranking);
            view.stockContainer = convertView.findViewById(R.id.stockContainer);
            view.imageView = convertView.findViewById(R.id.more_menu);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        Cryto cryto = crytos.get(position);
        if (cryto != null) {
            view.ticker.setText(cryto.getSymbol());
            view.namem.setText(cryto.getName());
            view.ranking.setText(cryto.getRank());
            //holder.stockContainer.setBackgroundColor(Color.parseColor(cryto.getColor()));
            double a = cryto.getLastPrice();
            double b = cryto.getNewPrice();

            view.stockContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, detail.class);
                    intent.putExtra("uuid", cryto.getUuid());
                    context.startActivity(intent);
                }
            });
            view.stockContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.UP);
            //double kq = (double) Math.round(((a-b) * 10000)) / 10000;
            double kq2 = b - a;
            double tron = (double) Math.round((((b - a) / a) * 100) * 100) / 100;
            if ((a - b) > 0) {
                view.changePercent2.setTextColor(context.getColor(R.color.error_red));
                view.changePercent.setTextColor(context.getColor(R.color.error_red));
                //holder.changePercent.setText( ( tron  + "%" ) );

            } else {
                view.changePercent2.setTextColor(context.getColor(R.color.positive_green));
                view.changePercent.setTextColor(context.getColor(R.color.positive_green));
                //holder.changePercent.setText((  tron + "%"));
            }

            view.changePercent2.setText((df.format(b) + ""));
            view.changePercent.setText((tron + "%"));
            view.imageView.setImageResource(R.drawable.ic_baseline_remove_circle_24);
            View finalConvertView = convertView;
            view.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)  {
                    preconfig.remove(context, cryto.getUuid());
                    notifyDataSetChanged();
                    onRefresh();

                }
            });
        }
        return convertView;
    }
    public void set(MyInterface listener){
        this.listener=listener;
    }

    @Override
    public void onRefresh() {
        listener.foo();
    }

    public interface MyInterface{
        public void foo();
    }
    public class ViewHolder {
        private TextView ticker, namem, totalValue, changePercent, changePercent2, ranking;
        private LinearLayout stockContainer;
        private ImageView imageView;

    }
}
