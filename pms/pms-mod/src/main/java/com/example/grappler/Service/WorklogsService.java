package com.example.grappler.Service;

import com.example.grappler.Entity.Tickets;
import com.example.grappler.Entity.Worklogs;
import com.example.grappler.Exception.TicketNotFoundException;
import com.example.grappler.Exception.WorklogNotFoundException;
import com.example.grappler.Repository.TicketRepository;
import com.example.grappler.Repository.WorklogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorklogsService {
    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Get a list of all worklogs.
     *
     * @return A list of worklogs.
     */
    public List<Worklogs> getAllWorklogs() {
        return worklogRepository.findAll();
    }

    /**
     * Get a worklog by ID.
     *
     * @param logId The ID of the worklog to retrieve.
     * @return The worklog with the specified ID.
     */
    public Worklogs getWorklogById(Long logId) {
        return worklogRepository.findById(logId)
                .orElseThrow(() -> new WorklogNotFoundException("Worklog not found"));
    }

    /**
     * Create a new worklog.
     *
     * @param worklog The worklog to create.
     * @return The created worklog.
     */
    public Worklogs createWorklog(Worklogs worklog) {
        return worklogRepository.save(worklog);
    }

    /**
     * Update an existing worklog.
     *
     * @param logId    The ID of the worklog to update.
     * @param updatedWorklog  The updated worklog data.
     * @return The updated worklog.
     */
    public Worklogs updateWorklog(Long logId, Worklogs updatedWorklog) {
        if (!worklogRepository.existsById(logId)) {
            throw new WorklogNotFoundException("Worklog not found");
        }
        updatedWorklog.setLog_id(logId);
        return worklogRepository.save(updatedWorklog);
    }

    /**
     * Delete a worklog by ID.
     *
     * @param logId The ID of the worklog to delete.
     */
    public void deleteWorklog(Long logId) {
        if (!worklogRepository.existsById(logId)) {
            throw new WorklogNotFoundException("Worklog not found");
        }
        worklogRepository.deleteById(logId);
    }

    public List<Worklogs> getWorklogsByTicketId(Long ticket_id) {
        // Assuming you have a repository for Worklogs
        List<Worklogs> worklogs = worklogRepository.findByTicketId(ticket_id);

        if (worklogs.isEmpty()) {
            // You can handle cases where no worklogs are found for the given ticketId
            throw new EntityNotFoundException("No worklogs found for ticket with ID: " + ticket_id);
        }

        return worklogs;
    }




//    public Worklogs addWorklogToTicket(Long ticketId, Worklogs worklog) {
//        // First, check if the ticket with the given ID exists
//        Tickets ticket = ticketRepository.findById(ticketId).orElse(null);
//
//        if (ticket == null) {
//            // Handle the case where the ticket doesn't exist, e.g., throw an exception or return an error message
//            throw new TicketNotFoundException("Ticket with ID " + ticketId + " not found");
//        }
//
//        // Set the ticket for the worklog
//        worklog.setTickets(ticket);
//
//        // You may want to perform additional validation or business logic here
//
//        // Save the worklog to the database
//        Worklogs addedWorklog = worklogsRepository.save(worklog);
//
//        return addedWorklog;
//    }
}
