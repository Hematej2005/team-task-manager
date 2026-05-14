package com.taskmanager.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    private String title;
    private String description;
    private Long assignedMemberId;
    private LocalDate deadline;
    private Long projectId;
}