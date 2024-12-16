package com.example.project167.domain;

public class CategoryDomain {
    private String name;
    private String picPath;

    public CategoryDomain(String name, String picPath) {
        this.name = name;
        this.picPath = picPath;
    }

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
