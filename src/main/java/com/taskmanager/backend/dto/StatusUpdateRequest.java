package com.taskmanager.backend.dto;

import com.taskmanager.backend.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequest {

    private TaskStatus status;
}