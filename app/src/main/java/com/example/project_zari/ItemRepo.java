package com.example.project_zari;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemRepo {

    private static ItemRepo itemrepo = new ItemRepo();
    private ArrayList<DemoItem2> items = new ArrayList<>();


    public static ItemRepo getInstance(){
        return itemrepo;
    }

    private ItemRepo(){}

    //PRETENDING TO GET DATA FROM WEB SERVICE
    public List<DemoItem2> getItems(){

        setItems();
        List<DemoItem2> item2 = new ArrayList<>(items);
        //item2.setValue(items);
        return item2;
    }

    public void setItems(){


    }
}
