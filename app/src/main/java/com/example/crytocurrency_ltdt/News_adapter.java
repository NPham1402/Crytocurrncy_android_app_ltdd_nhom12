package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class News_adapter extends RecyclerView.Adapter<News_adapter.ViewHolder> {
    private ArrayList<News> newsArrayList;
    private Context context;
    public News_adapter(ArrayList<News> newsArrayList,Context context){
        this.context=context;
        this.newsArrayList=newsArrayList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,date,sysmbol;
        public ViewHolder(@NonNull View item_view){
            super(item_view);
            title=item_view.findViewById(R.id.tv_title);
            date=item_view.findViewById(R.id.tv_date_public);
            sysmbol=item_view.findViewById(R.id.tv_sysbol_currency);
        }
    }

    @NonNull
    @Override
    public News_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
        return new News_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_adapter.ViewHolder holder, int position) {
    News news=newsArrayList.get(position);
    holder.date.setText(news.getPublished_at());
    holder.title.setText(news.getTitle_post());
    holder.sysmbol.setText(news.getDomain());
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


}
