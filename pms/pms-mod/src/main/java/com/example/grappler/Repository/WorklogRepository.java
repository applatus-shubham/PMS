package com.example.grappler.Repository;

import com.example.grappler.Entity.Tickets;
import com.example.grappler.Entity.Worklogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface WorklogRepository extends JpaRepository<Worklogs ,Long> {
    @Query("SELECT w FROM Worklogs w WHERE w.tickets.ticketId = :ticketId")
    List<Worklogs> findByTicketId(Long ticketId);

}
