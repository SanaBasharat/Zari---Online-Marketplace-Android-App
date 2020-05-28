package com.example.project_zari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Product_View extends AppCompatActivity {

    private TextView prod_name, prod_desc, prod_price;
    private ImageView prod_image;
    private RatingBar prod_rating;
    Button btn;
    Cart cart = Cart.getInstance();

    FavItem fav_item = new FavItem();

    private static final String TAG = "signInBuyer";
    DatabaseReference reff;
    DatabaseReference reff2;
    ImageView favbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__view);


        reff = FirebaseDatabase.getInstance().getReference().child("BuyersFav");
        reff2 = FirebaseDatabase.getInstance().getReference("BuyersFav");
        SharedPreferences preferences = this.getSharedPreferences("buyersignin", Context.MODE_PRIVATE);

        final String cust_email = preferences.getString("email", "Email: ");


        //getting view ids
        favbtn = (ImageView) findViewById(R.id.favs);
        prod_name = (TextView) findViewById(R.id.prod_name);
        prod_desc = (TextView) findViewById(R.id.proddesctext);
        prod_price = (TextView) findViewById(R.id.price);
        prod_image = (ImageView) findViewById(R.id.prod_image);
        prod_rating = (RatingBar) findViewById(R.id.prodratingBar);

        //getting value from pervious activity
        final Intent intent = getIntent();
        final String title = intent.getExtras().getString("Product Name");
        final String price = intent.getExtras().getString("Product Price");
        final int icon = intent.getExtras().getInt("Image");
        final Float rating = intent.getExtras().getFloat("Product Rating");
        final String desc = intent.getExtras().getString("Product Desc");




        //setting values
        prod_name.setText(title);
        prod_desc.setText(desc);
        prod_price.setText(price);
        prod_image.setImageResource(icon);
        prod_rating.setRating(rating);

        //getting and setting value from elegant number button
        final ElegantNumberButton numpicker = (ElegantNumberButton) findViewById(R.id.numpicker);
        numpicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = numpicker.getNumber();
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fav_item.setProdDesc(prod_desc.getText().toString());
                fav_item.setProdTitle(prod_name.getText().toString());
                fav_item.setProdPrice(prod_price.getText().toString());
                fav_item.setProdRating(rating);
                fav_item.setbEmail(cust_email);

                reff2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean found = false;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            //If email exists then toast shows else store the data on new key
                            String buyer_email = data.child("bEmail").getValue().toString();
                            String product_name = data.child("prodTitle").getValue().toString();
                            System.out.println(cust_email);
                            System.out.println(buyer_email);

                            if (cust_email.equals(buyer_email) && product_name.equals(title)) {
                                found = true;
                            }
                        }

                        if (found == false){
                            reff.push().setValue(fav_item);
                            Toast.makeText(Product_View.this, "Added to Favorites", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Product_View.this, "Item already exists in Favourites.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                    }
                });



                /*reff.push().setValue(fav_item);
                Toast.makeText(getApplicationContext(), "Added To Favourites", Toast.LENGTH_SHORT).show();
                favbtn.setColorFilter(getResources().getColor(R.color.zariPurple));*/
            }
        });
        btn = (Button) findViewById(R.id.addtocart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = intent.getExtras().getString("Product Name");
                String price = intent.getExtras().getString("Product Price");
                final int icon = intent.getExtras().getInt("Image");
                Float rating = intent.getExtras().getFloat("Product Rating");
                String desc = intent.getExtras().getString("Product Desc");
                String num = numpicker.getNumber();
                int i = Integer.parseInt(num);

                DemoItem2 obj = new DemoItem2(title,price,desc,icon,rating );
                obj.setQuantity(i);
                Toast.makeText(getApplicationContext(), "ADDED TO CART", Toast.LENGTH_SHORT).show();
                cart.addToCart(obj);
            }
        });


    }
}
