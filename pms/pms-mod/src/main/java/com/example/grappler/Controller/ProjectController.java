package com.example.grappler.Controller;

import com.example.grappler.Entity.Projects;
import com.example.grappler.Entity.Users;
import com.example.grappler.Exception.ResourceNotFoundException;
import com.example.grappler.Repository.ProjectRepository;
import com.example.grappler.Service.ProjectService;
import com.example.grappler.dto.ProjectsDTO;
import com.example.grappler.dto.TicketsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProjects() {
        try {
            System.out.println("Fetching all projects...");
            List<Projects> projects = projectRepository.findAll();
            if (projects.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No projects Found");
            } else {
                // Convert Projects entities to ProjectsDTO
                List<ProjectsDTO> projectDTOs = projects.stream()
                        .map(project -> {
                            ModelMapper modelMapper = new ModelMapper();

                            // Handle the conversion of PersistentBag to List
                            List<TicketsDTO> tickets = project.getTickets()
                                    .stream()
                                    .map(ticket -> modelMapper.map(ticket, TicketsDTO.class))
                                    .collect(Collectors.toList());

                            ProjectsDTO projectDTO = modelMapper.map(project, ProjectsDTO.class);
                            projectDTO.setTickets(tickets);
                            return projectDTO;
                        })
                        .collect(Collectors.toList());
                return ResponseEntity.ok(projectDTOs);
            }
        } catch (Exception e) {
            System.err.println("An error occurred while fetching projects: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching projects.");
        }
    }


    // Other contro

    @GetMapping("/{projectId}/ticket")
    public ResponseEntity<Projects> getTicketByProjectById(@PathVariable Long projectId) {
        Projects project = projectService.findProjectById(projectId);
        try {
            if (project == null) {
                return new ResponseEntity<>(project, HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");

        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @PostMapping
    public Projects create(@RequestBody Projects projects) {
        return projectRepository.save(projects);
    }
    @GetMapping("/{id}")
    public Projects getById(@PathVariable Long id) {
        Optional<Projects> project = projectRepository.findById(id);
        return project.get();
    }
    @DeleteMapping("/{id}")
    public Projects deleteById(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return null;
    }

}