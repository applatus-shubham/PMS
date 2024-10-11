package com.example.grappler.Controller;

import com.example.grappler.Entity.Tickets;
import com.example.grappler.Entity.Worklogs;
import com.example.grappler.Service.TicketService;
import com.example.grappler.Service.WorklogsService;
import com.example.grappler.dto.WorklogsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/worklogs")
 public class WorklogController {
    @Autowired
    private WorklogsService worklogsService;

    @Autowired
    TicketService ticketService;
    Logger logger= LoggerFactory.getLogger(com.example.grappler.Entity.Tickets.class);

    /**
     * Get a list of all worklogs.
     *
     * @return A list of worklogs.
     */
    private WorklogsDTO mapWorklogsToDTO(Worklogs worklogs) {
        WorklogsDTO worklogsDTO = new WorklogsDTO();

        worklogsDTO.setLog_id(worklogs.getLog_id());
        worklogsDTO.setDate(worklogs.getDate());
        worklogsDTO.setStart_time(worklogs.getStart_time());
        worklogsDTO.setEnd_time(worklogs.getEnd_time());
        worklogsDTO.setDuration(worklogs.getDuration());
        worklogsDTO.setTickets(worklogs.getTickets());

        return worklogsDTO;
    }
    @GetMapping
    public ResponseEntity<List<WorklogsDTO>> getAllWorklogs() {
        try {
            List<Worklogs> worklogs = worklogsService.getAllWorklogs();

            // Convert Worklogs entities to WorklogsDTO
            List<WorklogsDTO> worklogsDTOs = worklogs.stream()
                    .map(this::mapWorklogsToDTO)
                    .collect(Collectors.toList());

            logger.info("Retrieved all worklogs successfully.");
            return new ResponseEntity<>(worklogsDTOs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve all worklogs.", e);
            throw new RuntimeException("Failed to retrieve worklogs.", e);
        }
    }

    @GetMapping("/by-ticket/{ticketId}")
    public ResponseEntity<List<Worklogs>> getWorklogsByTicketIds(@PathVariable Long ticketId) {
        if (ticketId == null || ticketId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Worklogs> worklogs = worklogsService.getWorklogsByTicketId(ticketId);
            return new ResponseEntity<>(worklogs, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<WorklogsDTO>> getWorklogsByTicketId(@PathVariable Long ticketId) {
        try {
            List<Worklogs> worklogs = worklogsService.getWorklogsByTicketId(ticketId);

            // Convert Worklogs entities to WorklogsDTO
            List<WorklogsDTO> worklogsDTOs = worklogs.stream()
                    .map(this::mapWorklogsToDTO)
                    .collect(Collectors.toList());

            logger.info("Retrieved worklogs for ticket ID " + ticketId + " successfully.");
            return new ResponseEntity<>(worklogsDTOs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve worklogs for ticket ID " + ticketId, e);
            throw new RuntimeException("Failed to retrieve worklogs.", e);
        }
    }

    /**
     * Get a worklog by ID.
     *
     * @param logId The ID of the worklog to retrieve.
     * @return The worklog with the specified ID.
     */
    @GetMapping("/{logId}")
    public ResponseEntity<Worklogs> getWorklogById(@PathVariable Long logId) {
        Worklogs worklog = worklogsService.getWorklogById(logId);
        return new ResponseEntity<>(worklog, HttpStatus.OK);
    }

//    @PostMapping("/tickets/{ticketId}")
//    public ResponseEntity<Worklogs> addWorklogToTicket(@PathVariable Long ticketId, @RequestBody Worklogs worklog) {
//        Worklogs addedWorklog = worklogsService.addWorklogToTicket(ticketId, worklog);
//        return new ResponseEntity<>(addedWorklog, HttpStatus.CREATED);
//    }

    /**
     * Create a new worklog.
     *
     * @param worklog The worklog to create.
     * @return The created worklog.
     */
    @PostMapping
    public ResponseEntity<?> createWorklog(@RequestBody Worklogs worklog) {
        try {
            // Perform validation if needed
            if (worklog.getDate() == null || worklog.getDuration() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid worklog data.");
            }

            // You can implement similar logic for fetching related entities (e.g., projects, tickets, users)

            // Create a new Worklog instance
            Worklogs newWorklog = new Worklogs();
            newWorklog.setLog_id(worklog.getLog_id());
            newWorklog.setDate(worklog.getDate());
            newWorklog.setDuration(worklog.getDuration());
            newWorklog.setEnd_time(worklog.getEnd_time());
            newWorklog.setStart_time(worklog.getStart_time());

            // Save the new worklog
            Worklogs createdWorklog = worklogsService.createWorklog(newWorklog);

            logger.info("Created a new worklog with ID: {}", createdWorklog.getLog_id());

            return new ResponseEntity<>(createdWorklog, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while creating a worklog: " + e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Update an existing worklog.
     *
     * @param logId    The ID of the worklog to update.
     * @param worklog  The updated worklog data.
     * @return The updated worklog.
     */
    @PutMapping("/{logId}")
    public ResponseEntity<Worklogs> updateWorklog(@PathVariable Long logId, @RequestBody Worklogs worklog) {
        Worklogs updatedWorklog = worklogsService.updateWorklog(logId, worklog);
        return new ResponseEntity<>(updatedWorklog, HttpStatus.OK);
    }

    /**
     * Delete a worklog by ID.
     *
     * @param logId The ID of the worklog to delete.
     * @return HTTP status indicating the success of the operation.
     */
    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteWorklog(@PathVariable Long logId) {
        worklogsService.deleteWorklog(logId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




    @PostMapping("/{ticketId}")
    public ResponseEntity<?> createWorklog(@PathVariable Long ticketId, @RequestBody Worklogs worklog) {
        try {
            // Perform validation if needed
            if (worklog.getDate() == null || worklog.getDuration() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid worklog data.");
            }

            // You can implement similar logic for fetching related entities (e.g., projects, users)

            // Find the corresponding ticket
            Tickets ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found.");
            }

            // Create a new Worklog instance
            Worklogs newWorklog = new Worklogs();
            newWorklog.setLog_id(worklog.getLog_id());
            newWorklog.setDate(worklog.getDate());
            newWorklog.setDuration(worklog.getDuration());
            newWorklog.setEnd_time(worklog.getEnd_time());
            newWorklog.setStart_time(worklog.getStart_time());
            newWorklog.setTickets(ticket);  // Set the ticket

            // Save the new worklog
            Worklogs createdWorklog = worklogsService.createWorklog(newWorklog);

            logger.info("Created a new worklog with ID: {}", createdWorklog.getLog_id());

            return new ResponseEntity<>(createdWorklog, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while creating a worklog: " + e.getMessage(), e);
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
