package com.example.database;
import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;


public class items {

    @PrimaryKey (autoGenerate = true)
    private int id;

    public String title;
    public String desc;
    public Drawable icon;
    public float rating;
}
