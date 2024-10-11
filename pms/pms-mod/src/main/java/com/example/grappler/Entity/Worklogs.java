package com.example.grappler.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Worklogs {

    @ManyToOne
    private Users users;

    @ManyToOne
    @JsonIgnore
    private Projects projects;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ticketId")
    private Tickets tickets;

    @OneToMany(mappedBy = "worklogs" ,cascade=CascadeType.ALL)
    private List<AssignmentHistory> assignmentHistory ;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)@Column(name ="log_id",nullable=false ,columnDefinition = "SERIAL")Long log_id;

    private LocalDateTime date;
    private LocalDateTime start_time;//min
    private LocalDateTime end_time; //min
    private  int duration; //min


    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }

    public List<AssignmentHistory> getAssignmentHistory() {
        return assignmentHistory;
    }

    public void setAssignmentHistory(List<AssignmentHistory> assignmentHistory) {
        this.assignmentHistory = assignmentHistory;
    }
}
