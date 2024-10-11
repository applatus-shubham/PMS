package com.example.grappler.Entity;

import jakarta.persistence.*;
@Entity
public class Ticket_assign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)@Column(name ="Ticket_assign_id",nullable=false )Long ticket_assign_id;

    @ManyToOne
    private Tickets tickets;

    @ManyToOne
    private Users users;

}
