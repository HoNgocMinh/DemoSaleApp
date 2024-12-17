package com.example.project167.domain;

public class CategoryDomain {
    private int id;
    private String name;
    private String picPath;

    public CategoryDomain(int id, String name, String picPath) {
        this.id = id;
        this.name = name;
        this.picPath = picPath;
    }

    public int getId(){return id; }

    public void setId(int id){this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
