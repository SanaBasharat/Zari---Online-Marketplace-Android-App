package com.example.database.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import androidx.room.Room;

import com.example.database.customer;
import com.example.database.database.DatabaseClass;

import java.util.List;

import static androidx.room.Room.databaseBuilder;

public class customer_repo {

    private String DB_NAME = "app_core_db";
    private  DatabaseClass customer_db;

    public customer_repo(Context context) {
        getInstance(context);
    }


    private DatabaseClass getInstance(Context context){
        if (customer_db!=null){
            return customer_db;
        }else{
            customer_db = databaseBuilder(context, DatabaseClass.class, DB_NAME).build();
        }
        return customer_db;
    }

    public void insertTask(String email, String pass, String name, String dob, String gender)
    {
        customer cust = new customer();
        cust.setEmail(email);
        cust.setPassword(pass);
        cust.setName(name);
        cust.setDob(dob);
        cust.setGender(gender);
        insertTask(cust);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertTask(final customer cust){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.e("customer_repo", "Inserting!!!! from Background Thread: " + Thread.currentThread().getId());
                customer_db.customer_dao().insertTask(cust);
                return null;
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private customer getcustomer(final String email, final String pass){
        new AsyncTask<Void, Void, customer>() {
            @Override
            protected customer doInBackground(Void... voids) {
                return customer_db.customer_dao().getcustomer(email, pass);
            }

            @Override
            protected void onPostExecute(customer customer) {
                super.onPostExecute(customer);
            }
        }.execute();

        return null;
    }



}
