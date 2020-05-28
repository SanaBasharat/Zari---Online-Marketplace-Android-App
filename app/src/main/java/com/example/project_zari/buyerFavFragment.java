package com.example.project_zari;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class buyerFavFragment extends Fragment {

    List<DemoItem2> items;
    private ItemViewModel itemmodel;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    SharedPreferences sharedpreferences;
    String TAG = "buyerFavPage";

    public buyerFavFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        itemmodel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemmodel.init();
    }

    public static buyerFavFragment newInstance() {
        buyerFavFragment fragment = new buyerFavFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blank_bfav, container, false);
        final RecyclerView rv = view.findViewById(R.id.buyerfavrecycleview);

        items = new ArrayList<>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("buyersignin", Context.MODE_PRIVATE);


        final String cust_email = preferences.getString("email", "Name: ");

        mDatabase = FirebaseDatabase.getInstance().getReference("BuyersFav");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()) {

                    String desc = uniqueKeySnapShot.child("prodDesc").getValue().toString();
                    String email = uniqueKeySnapShot.child("bEmail").getValue().toString();
                    String name = uniqueKeySnapShot.child("prodTitle").getValue().toString();
                    String price = uniqueKeySnapShot.child("prodPrice").getValue().toString();
                    int rating = (int) uniqueKeySnapShot.child("prodRating").getValue(Integer.class);
                    float r = rating;

                    if (email.equals(cust_email)) {

                        final DemoItem2 obj = new DemoItem2();
                        obj.setTitle(name);
                        obj.setPrice(price);
                        obj.setDesc(desc);
                        obj.setRating(rating);

                        Random rand = new Random();
                        int n = rand.nextInt(6);

                        if( n==0)
                            obj.setIcon(R.drawable.shopitem1);
                        else if (n ==1)
                            obj.setIcon(R.drawable.shopitem2);
                        else if (n ==2)
                            obj.setIcon(R.drawable.shopitem3);
                        else if (n ==3)
                            obj.setIcon(R.drawable.shopitem4);
                        else if (n ==4)
                            obj.setIcon(R.drawable.shopitem5);
                        else if (n ==5)
                            obj.setIcon(R.drawable.shopitem6);

                        itemmodel.setItems(obj);
                    }

                }

                rv.setLayoutManager(new GridLayoutManager(getContext(), 1));

                rv.setAdapter(new bfav_Adapter(itemmodel.getItems(), getContext())); //get value problem
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                return;
            }
        });


        return view;
    }


}
