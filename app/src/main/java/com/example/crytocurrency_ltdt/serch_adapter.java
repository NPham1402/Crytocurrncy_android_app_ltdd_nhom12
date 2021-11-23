package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class serch_adapter extends RecyclerView.Adapter<serch_adapter.ViewHolder> {
    private ArrayList<Util.searc> crytos;
    private Context context;
    public serch_adapter(ArrayList<Util.searc> crytos, Context context) {
        this.crytos = crytos;
        this.context = context;
    }
    public void addlist(ArrayList<Util.searc> crytos){
        this.crytos=crytos;
    }
    @NonNull
    @Override
    public serch_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new serch_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull serch_adapter.ViewHolder holder, int position) {
        Util.searc searc = crytos.get(position);
    holder.textView.setText(searc.name);
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(context,detail.class);
            intent.putExtra("uuid",searc.uuid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    });
    holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });
    }

    @Override
    public int getItemCount() {
        return crytos.size();
    }
    public void clear(){
        crytos.clear();
        notifyDataSetChanged();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_search_item);
            imageView =itemView.findViewById(R.id.imv_add);
            linearLayout = itemView.findViewById(R.id.search);
        }
    }
}
