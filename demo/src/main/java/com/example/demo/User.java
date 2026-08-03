package com.example.demo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List ;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    private String name ;

    @Column(unique = true)
    private String email ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;

    private String phoneNum ;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Order> orders ;

    public long getId() {
        return id ;
    }
    public void setId(long id) {
        this.id = id ;
    }
    public String getName() {
        return name ;
    }
    public void setName(String name) {
        this.name = name ;
    }
    public String getEmail() {
        return email ;
    }
    public void setEmail(String email) {
        this.email = email ;
    }
    public String getPassword() {
        return password ;
    }
    public void setPassword(String password) {
        this.password = password ;
    }
    public List<Order> getOrders() {
        return orders ;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders ;
    }
    public String getPhoneNum() {
        return phoneNum ;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum ;
    }

}
