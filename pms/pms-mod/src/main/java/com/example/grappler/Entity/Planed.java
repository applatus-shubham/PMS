package com.example.grappler.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class Planed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "ticketid")
    private Tickets tickets;

    @ManyToOne
    @JoinColumn(name = "projectid")
    private Projects projects;

    private String priority;
private LocalDateTime start;
private LocalDateTime end;
    // Getters and setters

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
