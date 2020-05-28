package com.example.project_zari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signinBuyer extends AppCompatActivity {

    TextView txt;
    Button btn;
    private static final String TAG = "signInBuyer";
    private DatabaseReference mDatabase;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_buyer);

        mDatabase = FirebaseDatabase.getInstance().getReference("Customer");

        txt= (TextView) findViewById(R.id.redirectsignup);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signinBuyer.this, signup_buyer.class);
                startActivity(intent);
            }
        });

        btn = (Button) findViewById(R.id.bssignin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText uname = findViewById(R.id.bsemailid);
                if (uname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (uname.getText().toString().contains("@") != true)
                {
                    Toast.makeText(getApplicationContext(), "You must enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                final EditText pass = findViewById(R.id.bspassword);
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

                                    String dob = uniqueKeySnapShot.child("dob").getValue().toString();
                                    String gender = uniqueKeySnapShot.child("gender").getValue().toString();
                                    String name = uniqueKeySnapShot.child("name").getValue().toString();

                                    sharedpreferences = getSharedPreferences("buyersignin", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("email", email);
                                    editor.putString("name", name);
                                    editor.putString("dob", dob);
                                    editor.putString("gender", gender);

                                    editor.apply();

                                    Intent intent = new Intent(signinBuyer.this, buyer_homepage.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Failure to Sign In", Toast.LENGTH_SHORT).show();
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
