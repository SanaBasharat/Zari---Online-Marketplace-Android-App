package com.example.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.database.customer;

import java.util.List;

@Dao
public interface customer_dao {

    @Insert
    Long insertTask(customer customer);


    @Query("SELECT * FROM customer WHERE email =:email and password =:pass")
    customer getcustomer(String email, String pass);


}
