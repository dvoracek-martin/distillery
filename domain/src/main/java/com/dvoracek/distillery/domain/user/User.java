package com.dvoracek.distillery.domain.user;

import javax.persistence.*;

@Entity
@Table(name = "T_USER")
public class User {


    public User(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    // standard constructors / setters / getters / toString
}