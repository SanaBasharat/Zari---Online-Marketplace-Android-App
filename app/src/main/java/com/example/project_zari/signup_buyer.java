package com.example.project_zari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Context;

import com.example.database.repository.customer_repo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.R.style.Theme_Light;
import static android.R.style.Theme_Material_Dialog_MinWidth;
import static android.R.style.Theme_Material_Light_Dialog_MinWidth;

public class signup_buyer extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Button btn;
    String dob;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    DatabaseReference reff;
    DatabaseReference reff2;
    Customer cust;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_buyer);


        reff = FirebaseDatabase.getInstance().getReference().child("Customer");
        reff2 = FirebaseDatabase.getInstance().getReference("Customer");
        cust = new Customer();

        btn = (Button) findViewById(R.id.buyerdob);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(signup_buyer.this , Theme_Material_Dialog_MinWidth, onDateSetListener, year, month, day );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
               /* Log.d(TAG, "onDateSet: mm/dd/yyyy " + month + "/" + day + "/" + year);*/

                String date = month + "/" + day + "/" + year;
                dob = date;
                btn.setText(date);
            }
        };

        //checks
        Button btn1 = (Button) findViewById(R.id.buyersu);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText name = findViewById(R.id.bsuname);
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                final EditText email = findViewById(R.id.bsuemail);
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (email.getText().toString().contains("@") != true)
                {
                    Toast.makeText(getApplicationContext(), "You must enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText pass = findViewById(R.id.bsupass);
                if (pass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (pass.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(), "You password must be greater than 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText cpass = findViewById(R.id.bsuconfirmpass);
                if (cpass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter re-enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }




                //insert into db
                final String customeremail = email.getText().toString();
                String customerpass = pass.getText().toString();
                final String customername = name.getText().toString();
                final String[] gender = new String[1];
                final String dateofBirth = dob;
                radioGroup = (RadioGroup) findViewById(R.id.gendergroup);

                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                gender[0] =radioButton.getText().toString();

                cust.setName(customername);
                cust.setEmail(customeremail);
                cust.setPassword(customerpass);
                cust.setGender(gender[0]);
                cust.setDob(dateofBirth);

                reff2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean found = false;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            //If email exists then toast shows else store the data on new key
                            String email = data.child("email").getValue().toString();
                            System.out.println(email);
                            System.out.println(customeremail);
                            if (email.equals(customeremail)) {
                                found = true;

                            }
                        }

                        if (found == false){
                            reff.push().setValue(cust);
                            sharedpreferences = getSharedPreferences("buyersignin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("email", customeremail);
                            editor.putString("name", customername);
                            editor.putString("dob", dateofBirth);
                            editor.putString("gender", gender[0]);

                            editor.apply();
                            Toast.makeText(signup_buyer.this, "Redirecting", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signup_buyer.this, buyer_homepage.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(signup_buyer.this, "E-mail already exists.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(final DatabaseError databaseError) {
                    }
                });
                /*reff.push().setValue(cust);*/

            }
        });

    }
}
