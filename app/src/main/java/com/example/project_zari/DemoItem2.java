package com.example.project_zari;

import android.graphics.drawable.Drawable;

public class DemoItem2 {
    public String title;
    public String price;
    public int icon;
    public float rating;
    public String Desc;
    public int quantity = 100;


    DemoItem2(){}

    public int getQuantity() {
        return quantity;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public DemoItem2(String title, String p, String d, int i, float r) {
        this.title = title;
        this.price = p;
        this.icon = i;
        this.rating = r;
        this.Desc = d;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getIcon() {
        return icon;
    }

    public float getRating() {
        return rating;
    }

    public String getDesc() {
        return Desc;
    }
}
