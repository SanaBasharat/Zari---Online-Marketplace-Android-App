package com.example.project_zari;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class buyerHomeFragment extends Fragment {

    List<DemoItem2> items = new ArrayList<>();;
    TextView txt;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    SharedPreferences sharedpreferences;
    String TAG = "buyerHomePage";

    public buyerHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    public static buyerHomeFragment newInstance() {
        buyerHomeFragment fragment = new buyerHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_blank_bhome, container, false);
        final RecyclerView rv = view.findViewById(R.id.buyerhomerecycleview);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("buyersignin", Context.MODE_PRIVATE);


        String cust_name = preferences.getString("name", "Name: ");
        txt = (TextView) view.findViewById(R.id.welcome);
        txt.setText("Welcome " + cust_name );


        ImageButton btn = (ImageButton) view.findViewById(R.id.cart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), buyer_cart.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Item");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()){

                    String desc = uniqueKeySnapShot.child("desc").getValue().toString();
                    String email = uniqueKeySnapShot.child("email").getValue().toString();
                    String name = uniqueKeySnapShot.child("name").getValue().toString();
                    int price = (int) uniqueKeySnapShot.child("price").getValue(Integer.class);
                    int rating = (int) uniqueKeySnapShot.child("rating").getValue(Integer.class);
                    String p = "" + price;
                    float r = rating;
                    String image = uniqueKeySnapShot.child("image").getValue().toString();

                    final DemoItem2 obj = new DemoItem2();
                    obj.setDesc(desc);
                    obj.setTitle(name);
                    obj.setPrice(p);
                    obj.setRating(r);

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

                    items.add(obj);


                    //mStorageRef = FirebaseStorage.getInstance().getReference();

                    /*File localFile = null;
                    try {
                        String extension = "";
                        int i = image.lastIndexOf('.');
                        if (i > 0) {
                            extension = image.substring(i+1);
                        }
                        localFile = File.createTempFile(name, extension);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    StorageReference photoref = mStorageRef.child(image);

                    photoref.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageURL = uri.toString();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                        }
                    });*/


                }

                rv.setLayoutManager(new GridLayoutManager(getContext() , 2));
                rv.setAdapter(new bhome_Adapter(items, getContext()));
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Log.w(TAG, "Failed to read value.", error.toException());
                return;
            }
        });



        /*items.add(new DemoItem2("Kapra 2", "2345", "It is a cloth", R.drawable.shopitem2, 2));
        items.add(new DemoItem2("Kapra 3", "350", "It is a cloth", R.drawable.shopitem3, 3));
        items.add(new DemoItem2("Kapra 4", "400", "It is a cloth", R.drawable.shopitem4, 5));
        items.add(new DemoItem2("Kapra 1", "600", "It is a cloth", R.drawable.shopitem1, 2));
        items.add(new DemoItem2("Kapra 2", "650", "It is a cloth", R.drawable.shopitem2, 2));
        items.add(new DemoItem2("Kapra 3", "870", "It is a cloth", R.drawable.shopitem3, 3));
        items.add(new DemoItem2("Kapra 4", "90", "It is a cloth", R.drawable.shopitem4, 5));
        items.add(new DemoItem2("Kapra 1", "200", "It is a cloth", R.drawable.shopitem1, 2));
        items.add(new DemoItem2("Kapra 2", "265", "It is a cloth", R.drawable.shopitem2, 2));
        items.add(new DemoItem2("Kapra 3", "285", "It is a cloth", R.drawable.shopitem3, 3));
        items.add(new DemoItem2("Kapra 4", "325", "It is a cloth", R.drawable.shopitem4, 5));*/


        return view;
    }



}
