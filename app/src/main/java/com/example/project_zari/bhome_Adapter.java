package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class bhome_Adapter extends RecyclerView.Adapter<bhome_Adapter.bHomeViewHolder> {

    private List<DemoItem2> items;
    private Context context;

    public bhome_Adapter(List<DemoItem2> item, Context context) {
        this.items = item;
        this.context = context;
    }

    @NonNull
    @Override
    public bHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_home,parent,false);

        return new bHomeViewHolder(view);
    }


/*

*/


    @Override
    public void onBindViewHolder(@NonNull bHomeViewHolder holder, final int position) {

        holder.prod_image.setImageResource(items.get(position).icon);
        holder.prod_name.setText(items.get(position).title);
        holder.prod_rating.setRating(items.get(position).rating);
        holder.prod_price.setText(items.get(position).price);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Product_View.class);
                intent.putExtra("Image", items.get(position).getIcon());
                intent.putExtra("Product Name", items.get(position).getTitle());
                intent.putExtra("Product Rating", items.get(position).getRating());
                intent.putExtra("Product Price", items.get(position).getPrice());
                intent.putExtra("Product Desc", items.get(position).getDesc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class bHomeViewHolder extends RecyclerView.ViewHolder{

        ImageView prod_image;
        TextView prod_name;
        RatingBar prod_rating;
        TextView prod_price;
        LinearLayout linearLayout;

        public bHomeViewHolder(@NonNull View itemView) {
            super(itemView);

            prod_image = itemView.findViewById(R.id.prodicon);
            prod_name = itemView.findViewById(R.id.prodname);
            prod_rating = itemView.findViewById(R.id.ratingBar);
            prod_price = itemView.findViewById(R.id.price);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.homefrag);
        }
    }
}
