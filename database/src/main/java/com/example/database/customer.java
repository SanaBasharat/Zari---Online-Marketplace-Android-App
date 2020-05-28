package com.example.database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
public class customer {

    @NonNull
    @PrimaryKey (autoGenerate = false)
    private String email;

    @ColumnInfo
    private String password;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String dob;
    @ColumnInfo
    private String gender;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
