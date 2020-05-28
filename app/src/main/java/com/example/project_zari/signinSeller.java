package com.example.project_zari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signinSeller extends AppCompatActivity {

    TextView txt;
    private DatabaseReference mDatabase;
    SharedPreferences sharedpreferences;
    private static final String TAG = "signInBuyer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_seller);

        mDatabase = FirebaseDatabase.getInstance().getReference("Seller");

        txt = (TextView) findViewById(R.id.sredirectsignup);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signinSeller.this, signup_seller.class);
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.sssignin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText uname = findViewById(R.id.ssemailid);
                if (uname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (uname.getText().toString().contains("@") != true)
                {
                    Toast.makeText(getApplicationContext(), "You must enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                final EditText pass = findViewById(R.id.sspassword);
                if (pass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }



                // Read from the database
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for( DataSnapshot uniqueKeySnapShot : dataSnapshot.getChildren()){

                            String password = uniqueKeySnapShot.child("password").getValue().toString();
                            String email = uniqueKeySnapShot.child("email").getValue().toString();

                            if(email.equals(uname.getText().toString()) )
                            {
                                if (password.equals(pass.getText().toString())){
                                    Toast.makeText(getApplicationContext(),"Sign In Successful", Toast.LENGTH_SHORT).show();

                                    String address = uniqueKeySnapShot.child("address").getValue().toString();
                                    String logo = uniqueKeySnapShot.child("logo").getValue().toString();
                                    String phone = uniqueKeySnapShot.child("phone").getValue().toString();
                                    String name = uniqueKeySnapShot.child("name").getValue().toString();

                                    sharedpreferences = getSharedPreferences("sellersignin", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("email", email);
                                    editor.putString("address", address);
                                    editor.putString("logo", logo);
                                    editor.putString("phone", phone);
                                    editor.putString("name",name);

                                    editor.apply();

                                    Intent intent = new Intent(signinSeller.this, Seller_Home.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Failure to Sign In", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                        return;
                    }
                });
            }
        });
    }


}

