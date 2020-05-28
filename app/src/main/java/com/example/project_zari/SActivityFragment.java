package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SActivityFragment extends Fragment {

    private Context context;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedpreferences;
    Boolean hasitems;
    int hasitemscount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_seller_activity,container,false);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        hasitems = false;
        hasitemscount = 0;

        this.context=getContext();
        sharedpreferences = context.getSharedPreferences("sellersignin", Context.MODE_PRIVATE);
        final String selleremail = sharedpreferences.getString("email","");

        mDatabase = FirebaseDatabase.getInstance().getReference("Item");

        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int num1 = 0, num2 = 0, num3 = 0;
                int mostbought1=0, mostbought2=0, mostbought3=0;
                int r1 = 0, r2 = 0, r3 = 0;
                int best1 = 0, best2 = 0, best3 = 0;
                int counter=0;
                for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()){

                    String email = uniqueKeySnapShot.child("email").getValue().toString();

                    if(email.equals(selleremail) )
                    {
                        String sold = uniqueKeySnapShot.child("quantitysold").getValue().toString();
                        int s = Integer.parseInt(sold);
                        if (s>num1){
                            num3 = num2;
                            num2 = num1;
                            num1 = s;
                            mostbought3 = mostbought2;
                            mostbought2 = mostbought1;
                            mostbought1 = counter;
                        } else if (s>num2){
                            num3 = num2;
                            num2 = s;
                            mostbought3 = mostbought2;
                            mostbought2 = counter;
                        }
                        else if (s>num3){
                            num3 = s;
                            mostbought3 = counter;
                        }
                        String rate = uniqueKeySnapShot.child("rating").getValue().toString();
                        int r = Integer.parseInt(rate);
                        if (r>r1){
                            r3 = r2;
                            r2 = r1;
                            r1 = r;
                            best3 = best2;
                            best2 = best1;
                            best1 = counter;
                        } else if (r>r2){
                            r3 = r2;
                            r2 = r;
                            best3 = best2;
                            best2 = counter;
                        }
                        else if (r>r3){
                            r3 = r;
                            best3 = counter;
                        }
                        hasitemscount++;
                        if (hasitemscount==3){
                            hasitems = true;
                        }
                    }
                    counter++;
                }

                TextView besttext1 = view.findViewById(R.id.bestselltext1);
                TextView besttext2 = view.findViewById(R.id.bestselltext2);
                TextView besttext3 = view.findViewById(R.id.bestselltext3);

                RatingBar toprating1 = view.findViewById(R.id.toprating1);
                RatingBar toprating2 = view.findViewById(R.id.toprating2);
                RatingBar toprating3 = view.findViewById(R.id.toprating3);

                String quant;
                String rate;
                String s1="",s2="",s3="",t1="",t2="",t3="";
                int prodr;
                counter = 0;
                for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()) {
                    String email = uniqueKeySnapShot.child("email").getValue().toString();

                    if(email.equals(selleremail) ) {
                        if (counter == mostbought1) {
                            quant = uniqueKeySnapShot.child("quantitysold").getValue().toString();
                            quant = quant.concat(" sold");
                            besttext1.setText(quant);
                            s1 = uniqueKeySnapShot.child("image").getValue().toString();
                        } else if (counter == mostbought2) {
                            quant = uniqueKeySnapShot.child("quantitysold").getValue().toString();
                            quant = quant.concat(" sold");
                            besttext2.setText(quant);
                            s2 = uniqueKeySnapShot.child("image").getValue().toString();
                        } else if (counter == mostbought3) {
                            quant = uniqueKeySnapShot.child("quantitysold").getValue().toString();
                            quant = quant.concat(" sold");
                            besttext3.setText(quant);
                            s3 = uniqueKeySnapShot.child("image").getValue().toString();
                        }
                    }
                    counter++;

                }

                counter = 0;
                for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()) {
                    String email = uniqueKeySnapShot.child("email").getValue().toString();

                    if(email.equals(selleremail) ) {
                        if (counter == best1) {
                            rate = uniqueKeySnapShot.child("rating").getValue().toString();
                            prodr = Integer.parseInt(rate);
                            toprating1.setRating(prodr);
                            t1 = uniqueKeySnapShot.child("image").getValue().toString();
                        } else if (counter == best2) {
                            rate = uniqueKeySnapShot.child("rating").getValue().toString();
                            prodr = Integer.parseInt(rate);
                            toprating2.setRating(prodr);
                            t2 = uniqueKeySnapShot.child("image").getValue().toString();
                        } else if (counter == best3) {
                            rate = uniqueKeySnapShot.child("rating").getValue().toString();
                            prodr = Integer.parseInt(rate);
                            toprating3.setRating(prodr);
                            t3 = uniqueKeySnapShot.child("image").getValue().toString();
                        }
                    }
                    counter++;

                }

                final ImageView bestimg1 = view.findViewById(R.id.bestsellimg1);
                final ImageView bestimg2 = view.findViewById(R.id.bestsellimg2);
                final ImageView bestimg3 = view.findViewById(R.id.bestsellimg3);

                final ImageView topimg1 = view.findViewById(R.id.topimg1);
                final ImageView topimg2 = view.findViewById(R.id.topimg2);
                final ImageView topimg3 = view.findViewById(R.id.topimg3);

                if (hasitems == true) {
                    StorageReference photoref = mStorageRef.child(s1);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(bestimg1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Failed to download");
                        }
                    });

                    photoref = mStorageRef.child(s2);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(bestimg2);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Failed to download");
                        }
                    });

                    photoref = mStorageRef.child(s3);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(bestimg3);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Failed to download");
                        }
                    });


                    photoref = mStorageRef.child(t1);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(topimg1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Failed to download");
                        }
                    });

                    photoref = mStorageRef.child(t2);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(topimg2);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Failed to download");
                        }
                    });

                    photoref = mStorageRef.child(t3);
                    photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageURL = uri.toString();
                            Glide.with(context).load(imageURL).into(topimg3);
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
                else{
                    System.out.println("Error");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                return;
            }
        });

        return view;
    }
}
