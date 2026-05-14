package com.taskmanager.backend.dto;

import com.taskmanager.backend.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String assignedMember;
    private String adminName;
    private String projectName;
    private LocalDate deadline;
    private TaskStatus status;
}