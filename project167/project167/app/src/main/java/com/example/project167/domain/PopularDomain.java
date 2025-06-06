package com.example.project167.domain;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String title; //Tên khóa học
    private String picUrl; //Đường dẫn ảnh khóa học
    private int review; //Tổng số lượng comment trong khóa học
    private double score; //Số điểm được đánh giá
    private int numberInCart;
    private double price; //Giá khóa học
    private String description; //Mô tả khóa học

    public PopularDomain(String title, String picUrl, int review, double score, double price,String description) {
        this.title = title;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.price = price;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
