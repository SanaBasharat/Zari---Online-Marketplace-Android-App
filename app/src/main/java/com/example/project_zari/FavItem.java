package com.example.project_zari;

public class FavItem {

    public String prodTitle;
    public String prodPrice;
    public float prodRating;
    public String prodDesc;
    public String bEmail;

    FavItem(){}

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public float getProdRating() {
        return prodRating;
    }

    public void setProdRating(float prodRating) {
        this.prodRating = prodRating;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getbEmail() {
        return bEmail;
    }

    public void setbEmail(String bEmail) {
        this.bEmail = bEmail;
    }
}
