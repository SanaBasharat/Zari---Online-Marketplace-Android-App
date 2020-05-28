package com.example.project_zari;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class sellerShopAdapter extends RecyclerView.Adapter<sellerShopAdapter.ShopViewHolder> {

    private List<DemoItem> items;
    private Context context;
    private StorageReference mStorageRef;

    public sellerShopAdapter(List<DemoItem> i, Context context) {
        this.items = i;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shop_item_layout,parent,false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final sellerShopAdapter.ShopViewHolder holder, int position) {
        String t = items.get(position).title;
        holder.prodtitle.setText(t);
        String prodimg = items.get(position).icon;
        //setting image below
        float r = items.get(position).rating;
        holder.rating.setRating(r);
        t = items.get(position).desc;
        holder.proddesc.setText(t);
        int temp = items.get(position).price;
        String str = Integer.toString(temp);
        holder.price.setText(str);
        temp = items.get(position).sold;
        str = Integer.toString(temp);
        holder.quant.setText(str);


        StorageReference photoref = mStorageRef.child(prodimg);

        photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageView temp = holder.prodimg;
                        String imageURL = uri.toString();
                        Glide.with(context).load(imageURL).into(temp);
                        holder.prodimg.setImageURI(uri);        ////WHAAAAAATTTT
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                System.out.println("Failed to download");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ShopViewHolder extends RecyclerView.ViewHolder{
        ImageView prodimg;
        TextView prodtitle;
        RatingBar rating;
        TextView proddesc;
        TextView price;
        TextView quant;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            prodimg = itemView.findViewById(R.id.prod_icon);
            prodtitle = itemView.findViewById(R.id.prod_name);
            rating = itemView.findViewById(R.id.ratingBar);
            proddesc = itemView.findViewById(R.id.prod_desc);
            price = itemView.findViewById(R.id.pricetextView);
            quant = itemView.findViewById(R.id.quant);
        }
    }
}
