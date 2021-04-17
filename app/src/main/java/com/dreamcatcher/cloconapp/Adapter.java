package com.dreamcatcher.cloconapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList itemIdList, itemTitleList, itemDetailList, itemImageUrlList;

    Adapter(Context context, ArrayList itemIdList, ArrayList itemTitleList, ArrayList itemDetailList, ArrayList itemImageUrlList){
        this.context=context;
        this.itemIdList = itemIdList;
        this.itemTitleList = itemTitleList;
        this.itemDetailList = itemDetailList;
        this.itemImageUrlList = itemImageUrlList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {
        holder.itemIdText.setText(String.valueOf(itemIdList.get(position)));
        holder.itemTitleText.setText(String.valueOf(itemTitleList.get(position)));
        holder.itemDetailText.setText(String.valueOf(itemDetailList.get(position)));
        holder.itemImageUrlText.setText(String.valueOf(itemImageUrlList.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemActivity.class);

                intent.putExtra("id", String.valueOf(itemIdList.get(position)));
                intent.putExtra("title", String.valueOf(itemTitleList.get(position)));
                intent.putExtra("detail", String.valueOf(itemDetailList.get(position)));
                intent.putExtra("imageUrl", String.valueOf(itemImageUrlList.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemIdList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemIdText, itemTitleText, itemDetailText, itemImageUrlText;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIdText = itemView.findViewById(R.id.itemlist_id);
            itemTitleText = itemView.findViewById(R.id.itemlist_title);
            itemDetailText = itemView.findViewById(R.id.itemlist_detail);
            itemImageUrlText = itemView.findViewById(R.id.itemlist_imageUrl);

            linearLayout = itemView.findViewById(R.id.itemlist);

        }
    }
}

