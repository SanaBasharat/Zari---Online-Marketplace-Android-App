package com.example.project_zari;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart c = new Cart();

    List<DemoItem2>  cart = new ArrayList<>();

    private Cart(){}

    public void addToCart(DemoItem2 obj){

        if(cart != null)
            cart.add(obj);
        else if (cart == null)
            cart = new ArrayList<>();
    }

    public static Cart getInstance() {
        return c;
    }

    public List<DemoItem2> getItems(){
        return cart;
    }

    public void emptyCart(){
        cart= new ArrayList<>();
    }
}
