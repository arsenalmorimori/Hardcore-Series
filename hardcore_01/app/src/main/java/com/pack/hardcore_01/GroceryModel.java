package com.pack.hardcore_01;

public class GroceryModel {

    private int id;
    private String name;
    private int price;

    public GroceryModel( int id, String name, int price) {
        this.price = price;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "groceryModel{" +
                "price=" + price +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
