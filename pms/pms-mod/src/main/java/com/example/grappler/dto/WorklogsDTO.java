package com.example.grappler.dto;

import com.example.grappler.Entity.Tickets;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorklogsDTO {
    private Long log_id;
    private LocalDateTime date;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int duration;
    private Tickets tickets;

}

