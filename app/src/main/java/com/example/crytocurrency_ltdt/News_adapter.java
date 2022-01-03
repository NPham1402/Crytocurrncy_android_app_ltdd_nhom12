package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    public void add(ArrayList<News> newsArrayList){
        this.newsArrayList=newsArrayList;
    }
    public  void clear(){
        newsArrayList.clear();
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,date,sysmbol,descrition;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View item_view){
            super(item_view);
            title=item_view.findViewById(R.id.tv_title);
            date=item_view.findViewById(R.id.tv_date_public);
            sysmbol=item_view.findViewById(R.id.tv_sysbol_currency);
            descrition=item_view.findViewById(R.id.news_subtitle);
            linearLayout=item_view.findViewById(R.id.newsContainer);

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
    holder.descrition.setText(news.getDescrition());
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            context.startActivity(browserIntent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


}
