package com.example.grappler.Controller;

import com.example.grappler.Entity.Projects;
import com.example.grappler.Entity.Tickets;
import com.example.grappler.Entity.Users;
import com.example.grappler.Exception.TicketNotFoundException;
import com.example.grappler.Exception.UserNotFoundException;
import com.example.grappler.Repository.UserRepository;
import com.example.grappler.Service.TicketService;
import com.example.grappler.dto.ProjectsDTO;
import com.example.grappler.dto.TicketsDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/tickets")
class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
            private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(TicketController.class);


    private TicketsDTO mapTicketsToDTO(Tickets tickets) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tickets, TicketsDTO.class);
    }
    /**
     * Get a list of all tickets
     *
     * @return ResponseEntity containing a list of Tickets
     */
    @GetMapping
    public ResponseEntity<List<TicketsDTO>> getAllTickets() {
        try {
            List<Tickets> tickets = ticketService.getAllTickets();
            List<TicketsDTO> ticketDTOs = tickets.stream()
                    .map(this::mapTicketsToDTO)
                    .collect(Collectors.toList());

            logger.info("Retrieved all tickets successfully.");
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve all tickets.", e);
            throw new RuntimeException("Failed to retrieve tickets.", e);
        }
    }



    /**
     * Get a ticket by ID
     *
     * @param ticketId The ID of the ticket to retrieve
     * @return ResponseEntity containing the requested Ticket
     */
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicketById(@PathVariable Long ticketId) {
        try {
            Tickets ticket = ticketService.getTicketById(ticketId);
            logger.info("Retrieved ticket with ID: {}", ticketId);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (TicketNotFoundException ex) {
            logger.error("Ticket not found with ID: {}", ticketId, ex);
            throw ex;
        } catch (Exception e) {
            logger.error("Failed to retrieve ticket with ID: {}", ticketId, e);
            throw new RuntimeException("Failed to retrieve the ticket.", e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Tickets>> getTicketsByUserId(@PathVariable Long id) {
        try {
            List<Tickets> tickets = ticketService.getTicketsByUserId(id);
            if (tickets.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("Retrieved tickets for User ID: {}", id);
                return new ResponseEntity<>(tickets, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Failed to retrieve tickets for User ID: {}", id, e);
            throw new RuntimeException("Failed to retrieve the tickets.", e);
        }
    }


    @GetMapping("/projects/{projectId}/tickets")
    public ResponseEntity<List<Tickets>> getTicketsByProjectId(@PathVariable Long projectId) {
        try {
            List<Tickets> tickets = ticketService.getTicketsByProjectId(projectId);
            logger.info("Retrieved tickets for project with ID: {}", projectId);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve tickets for project with ID: {}", projectId, e);
            throw new RuntimeException("Failed to retrieve the tickets.", e);
        }
    }

    /**
     * Create a new ticket
     *
     * @param ticket The Ticket object to create
     * @return ResponseEntity containing the created Ticket
     */
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Tickets ticket) {
        try {
            if (ticket.getUserIds() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User IDs cannot be null.");
            }

            List<Users> users = new ArrayList<>();
            for (Long userId : ticket.getUserIds()) {
                Users user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
                users.add(user);
            }
            Tickets ticket1 = new Tickets();
            ticket1.setTicketId(ticket.getTicketId());
            ticket1.setTitle(ticket.getTitle());
            ticket1.setDesciption(ticket.getDesciption());
            ticket1.setEstimatedTime(ticket.getEstimatedTime());
            ticket1.setStart_time(ticket.getStart_time());
            ticket1.setEnd_time(ticket.getEnd_time());
            ticket1.setPriority(ticket.getPriority());
            ticket1.setUserIds(ticket.getUserIds());
            ticket1.setUsers(users);
            Tickets createdTicket = ticketService.createTicket(ticket1);
            logger.info("Created a new ticket with ID: {}", createdTicket.getTicketId());
            return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            logger.error("User not found: " + e.getMessage(), e);
            return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred while creating a ticket: " + e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing ticket
     *
     * @param ticketId The ID of the ticket to update
     * @param ticket   The updated Ticket object
     * @return ResponseEntity containing the updated Ticket
     */
    @PutMapping("/{ticketId}")
    public ResponseEntity<Tickets> updateTicket(@PathVariable Long ticketId, @RequestBody Tickets ticket) {
        try {
            Tickets updatedTicket = ticketService.updateTicket(ticketId, ticket);
            logger.info("Updated ticket with ID: {}", updatedTicket.getTicketId());
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } catch (TicketNotFoundException e) {
            logger.error("Ticket not found when updating", e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to update the ticket", e);
            throw new RuntimeException("Failed to update the ticket.", e);
        }
    }

    /**
     * Delete a ticket by ID
     *
     * @param ticketId The ID of the ticket to delete
     * @return ResponseEntity with no content (204)
     */
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
            logger.info("Deleted ticket with ID: {}", ticketId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TicketNotFoundException e) {
            logger.error("Ticket not found when deleting", e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to delete the ticket", e);
            throw new RuntimeException("Failed to delete the ticket.", e);
        }
    }
}
