package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class bfav_Adapter extends RecyclerView.Adapter<bfav_Adapter.bFavViewHolder> {

    private List<DemoItem2> items;
    private Context context;

    public bfav_Adapter(List<DemoItem2> item, Context context) {
        this.items = item;
        this.context = context;
    }

    @NonNull
    @Override
    public bFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_favorite,parent,false);
        return new bFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bFavViewHolder holder, final int position) {

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


    public class bFavViewHolder extends RecyclerView.ViewHolder{

        ImageView prod_image;
        TextView prod_name;
        RatingBar prod_rating;
        TextView prod_price;
        LinearLayout linearLayout;

        public bFavViewHolder(@NonNull View itemView) {
            super(itemView);

            prod_image = itemView.findViewById(R.id.prodicon);
            prod_name = itemView.findViewById(R.id.prodname);
            prod_rating = itemView.findViewById(R.id.ratingBar);
            prod_price = itemView.findViewById(R.id.price);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.favfrag);
        }
    }
}
