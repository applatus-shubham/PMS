package com.example.grappler.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectsDTO {
    private Long projectId;
    private String name;
    private String description;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private List<TicketsDTO> tickets;

}
