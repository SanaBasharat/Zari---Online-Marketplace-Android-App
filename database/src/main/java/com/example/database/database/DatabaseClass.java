package com.example.database.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.database.customer;
import com.example.database.dao.customer_dao;

@Database(entities = {customer.class }, version = 1, exportSchema = false)
public abstract class DatabaseClass extends RoomDatabase {

    public abstract customer_dao customer_dao();
}
