package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class serch_adapter extends RecyclerView.Adapter<serch_adapter.ViewHolder> {
    private ArrayList<String> crytos;
    private Context context;
    public serch_adapter(ArrayList<String> crytos, Context context) {
        this.crytos = crytos;
        this.context = context;
    }
    public void addlist(ArrayList<String> crytos){
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
    String a=crytos.get(position);
    holder.textView.setText(a);
    holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context,R.string.ADD,Toast.LENGTH_SHORT).show();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_search_item);
            imageView =itemView.findViewById(R.id.imv_add);
        }
    }
}
