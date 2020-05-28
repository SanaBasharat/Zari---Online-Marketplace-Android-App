package com.example.project_zari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class buyer_cart extends AppCompatActivity {

    List<DemoItem2> items;
    private String CHANNEL_ID;
    int totalBill =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_cart);


        final Cart cart = Cart.getInstance();

        items = cart.getItems();

        int n;
        for (int i=0; i< items.size(); i++){

            String p = items.get(i).getPrice();
            n = Integer.parseInt(p);

            totalBill = totalBill + n*items.get(i).quantity;
        }
        TextView tv = (TextView) findViewById(R.id.totalbill);
        String bill = "Total Bill: "+ totalBill;
        tv.setText(bill);

        RecyclerView rv = (RecyclerView) findViewById(R.id.buyercartrecycleview);

        rv.setLayoutManager(new GridLayoutManager(this , 1));
        rv.setAdapter(new bCartAdapter(cart.getItems(), this));


        Button btn = findViewById(R.id.placeorder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cart.getItems().size()==0)
                    Toast.makeText(getApplicationContext(), "Fill your cart first to place order", Toast.LENGTH_SHORT).show();
                else {
                    cart.emptyCart();
                    Intent intent = new Intent(v.getContext(), order_placed.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

}
