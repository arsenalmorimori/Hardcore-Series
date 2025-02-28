package com.pack.hardcore_05;

public class StoreModel {

    private int id;
    private String item;
    private int price;

    public StoreModel(int id, String item, int price) {
        this.id = id;
        this.item = item;
        this.price = price;
    }

    @Override
    public String toString() {
        return id +
                "   " + item +
                "   :   " + price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
