package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SShopFragment extends Fragment {
    private DatabaseReference mDatabase;
    private SharedPreferences sharedpreferences;
    private Context mContext;

//    @Override
//    public View onCreate(){
//
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_shop,container,false);

        RecyclerView shopitemslist = view.findViewById(R.id.shop_recyclerview);
        shopitemslist.setLayoutManager(new GridLayoutManager(getContext(),2));

        FloatingActionButton btn = view.findViewById(R.id.addProductButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFloatingClicked();
            }
        });

        final List<DemoItem> shopitems=new ArrayList<>();
        int id;
        String s;
        Drawable d;

        final sellerShopAdapter adapt = new sellerShopAdapter(shopitems,getContext());
        shopitemslist.setAdapter(adapt);

        this.mContext=getContext();
        sharedpreferences = mContext.getSharedPreferences("sellersignin", Context.MODE_PRIVATE);
        final String selleremail = sharedpreferences.getString("email","");

        mDatabase = FirebaseDatabase.getInstance().getReference("Item");

        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()){

                    String email = uniqueKeySnapShot.child("email").getValue().toString();

                    if(email.equals(selleremail) )
                    {
                        String name = uniqueKeySnapShot.child("name").getValue().toString();
                        String desc = uniqueKeySnapShot.child("desc").getValue().toString();
                        String price = uniqueKeySnapShot.child("price").getValue().toString();
                        int p = Integer.parseInt(price);
                        String sold = uniqueKeySnapShot.child("quantitysold").getValue().toString();
                        int s = Integer.parseInt(sold);
                        String rate = uniqueKeySnapShot.child("rating").getValue().toString();
                        int r = Integer.parseInt(rate);
                        String img = uniqueKeySnapShot.child("image").toString();

                        DemoItem newitem = new DemoItem(name,desc,img,r,p,s);
                        shopitems.add(newitem);
                    }
                }
                adapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                return;
            }
        });


        adapt.notifyDataSetChanged();
        return view;
    }

    public void openFloatingClicked()
    {
        Intent intent = new Intent(getContext(), SellerAddProduct.class);
        startActivity(intent);
    }
}
