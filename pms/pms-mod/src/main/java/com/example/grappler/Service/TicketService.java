package com.example.grappler.Service;

import com.example.grappler.Entity.Tickets;
import com.example.grappler.Exception.TicketNotFoundException;
import com.example.grappler.Repository.TicketRepository;
import com.example.grappler.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketsRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketsRepository = ticketRepository;
    }
    public List<Tickets> getTicketsByUserId(Long userId) {
        return ticketsRepository.findTicketsByUserId(userId);
    }


    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }
    public Tickets getTicketById(Long ticketId) {
        return ticketsRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
    }

    public Tickets createTicket(Tickets ticket) {
        return ticketsRepository.save(ticket);
    }

    public Tickets updateTicket(Long ticketId, Tickets updatedTicket) {
        if (!ticketsRepository.existsById(ticketId)) {
            throw new TicketNotFoundException("Ticket not found");
        }

        updatedTicket.setTicketId(ticketId);
        return ticketsRepository.save(updatedTicket);
    }

    public void deleteTicket(Long ticketId) {
        if (!ticketsRepository.existsById(ticketId)) {
            throw new TicketNotFoundException("Ticket not found");
        }
        ticketsRepository.deleteById(ticketId);
    }

    public List<Tickets> getTicketsByProjectId(Long projectId) {
             return projectRepository.findByProjectId(projectId);
    }

}
