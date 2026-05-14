package com.taskmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectResponse {

    private Long id;

    private String projectName;

    private String description;

    private String createdBy;
}