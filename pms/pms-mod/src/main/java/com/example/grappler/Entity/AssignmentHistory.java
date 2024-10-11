package com.example.grappler.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class AssignmentHistory {
    @OneToMany(mappedBy = "assignmentHistory" ,cascade=CascadeType.ALL)
    private List<Tickets> tickets;
    @ManyToOne
    @JsonIgnore
    private Projects projects;

    @ManyToOne
    @JsonIgnore
    private Worklogs worklogs;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)@Column(name ="assigned_id",nullable=false )Long assigned_id;
    String previous_assignee;

    String new_assignee;

    public AssignmentHistory() {
    }

    public AssignmentHistory(Long assigned_id, String previous_assignee, String new_assignee) {
        this.assigned_id = assigned_id;
        this.previous_assignee = previous_assignee;
        this.new_assignee = new_assignee;
    }

    public Long getAssigned_id() {
        return assigned_id;
    }

    public void setAssigned_id(Long assigned_id) {
        this.assigned_id = assigned_id;
    }

    public String getPrevious_assignee() {
        return previous_assignee;
    }

    public void setPrevious_assignee(String previous_assignee) {
        this.previous_assignee = previous_assignee;
    }

    public String getNew_assignee() {
        return new_assignee;
    }

    public void setNew_assignee(String new_assignee) {
        this.new_assignee = new_assignee;
    }
}
