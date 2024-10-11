package com.example.grappler.dto;

import com.example.grappler.Entity.Worklogs;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketsDTO {
    @JsonProperty("ticketId")
    private Long ticketId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("estimatedTime")
    private LocalDateTime estimatedTime;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("startTime")
    private LocalDateTime startTime;
    @JsonProperty("endTime")
    private LocalDateTime endTime;
    @JsonProperty("userIds")
    private List<Long> userIds;
    @JsonProperty("worklogs")
    private List<Worklogs> worklogs;

    // Constructors, getters, setters, and other methods
}
