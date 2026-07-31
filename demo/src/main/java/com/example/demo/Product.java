package com.example.demo;

import jakarta.persistence.Entity ;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id ;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    private String name ;
    private String size ;
    private int price ;
    private String imageURL ;

    public String getName() {
        return name ;
    }
    public void setName(String name) {
        this.name = name ;
    }

    public long getId() {
        return id ;
    }
    public void setId(long id) {
        this.id = id ;
    }

    public String getSize() {
        return this.size ;
    }
    public void setSize(String size) {
        this.size = size ;
    }

    public int getPrice() {
        return this.price ;
    }
    public void setPrice(int price) {
        this.price = price ;
    }

    public String getImageURL() {
        return imageURL ;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL ;
    }
}
