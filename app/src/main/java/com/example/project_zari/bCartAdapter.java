package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class bCartAdapter extends RecyclerView.Adapter<bCartAdapter.bCartViewHolder> {

    private List<DemoItem2> items;
    private Context context;


    public bCartAdapter(List<DemoItem2> item, Context context) {
        this.items = item;
        this.context = context;
    }

    @NonNull
    @Override
    public bCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.buyer_cart_items,parent,false);

        return new bCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bCartViewHolder holder, final int position) {

        holder.prod_image.setImageResource(items.get(position).icon);
        holder.prod_name.setText(items.get(position).title);
        holder.prod_price.setText(items.get(position).price);
        int n = items.get(position).quantity;
        String quan = "Quantity: "+ n;
        holder.quantity.setText(quan);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class bCartViewHolder extends RecyclerView.ViewHolder{

        ImageView prod_image;
        TextView prod_name;
        TextView prod_price;
        TextView quantity;
        LinearLayout linearLayout;

        public bCartViewHolder(@NonNull View itemView) {
            super(itemView);

            prod_image = itemView.findViewById(R.id.prodicon);
            prod_name = itemView.findViewById(R.id.prodname);
            prod_price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.cartactivity);
        }
    }
}
