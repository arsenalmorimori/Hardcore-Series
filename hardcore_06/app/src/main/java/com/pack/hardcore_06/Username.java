package com.pack.hardcore_06;

public class Username {
    private int id;
    private String name;

    public Username(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id +
                " : " + name;
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
