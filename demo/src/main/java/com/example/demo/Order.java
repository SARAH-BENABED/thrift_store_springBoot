package com.example.demo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List ;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    private double totalPrice ;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference
    private User user ;

    private String guestName ;
    private String guestEmail ;
    private String guestPhone ;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> orderItems ;

    private String verificationCode ;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems ;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
