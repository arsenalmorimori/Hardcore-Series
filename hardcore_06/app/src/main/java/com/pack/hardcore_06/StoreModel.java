package com.pack.hardcore_06;

public class StoreModel {

    private int id;
    private String item;
    private int quantity;

    @Override
    public String toString() {
        return  id +
                "     " + item +
                "  :  " + quantity;
    }

    public StoreModel(int id, String item, int quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
