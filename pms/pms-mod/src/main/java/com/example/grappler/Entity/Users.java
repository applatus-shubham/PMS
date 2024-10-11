package com.example.grappler.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.jdbc.Work;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Users {

    @Id@GeneratedValue(strategy = GenerationType.SEQUENCE)@Column(name ="user_id",nullable=false )Long id;
    String username;
    String name;
    String email;
    Role role;

    @OneToMany(mappedBy = "users" ,cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Ticket_assign> ticket_assign;

    @OneToMany(mappedBy = "users" ,cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Worklogs> worklogs;

    @ManyToMany(mappedBy = "users")
    private List<Tickets> tickets;

    @OneToMany(mappedBy = "users",cascade =CascadeType.ALL)
    private List<Planed> planed;

   

    public Users() {
    }

    public Users(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

