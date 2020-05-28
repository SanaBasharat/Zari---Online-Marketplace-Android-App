package com.example.project_zari;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemViewModel extends ViewModel {

    List<DemoItem2> items ;
    private ItemRepo itemRepo ;

    public void init(){

        if(items!= null)
            return;
        itemRepo =ItemRepo.getInstance();
        items = itemRepo.getItems();
    }
    public List<DemoItem2> getItems(){
        return items;
    }

    public void setItems(DemoItem2 obj){
        items.add(obj);
    }

}
