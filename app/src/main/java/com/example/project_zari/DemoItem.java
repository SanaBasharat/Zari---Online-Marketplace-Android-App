package com.example.project_zari;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RatingBar;

public class DemoItem {
    public String title;
    public String desc;
    public String icon;
    public float rating;
    public int price;
    public int sold;

    public DemoItem(String title, String d, String i, float r, int p, int s) {
        this.title = title;
        this.desc = d;
        this.icon = i;
        this.rating = r;
        this.price = p;
        this.sold = s;
    }

}
