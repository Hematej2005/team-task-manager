package com.taskmanager.backend.service;

import com.taskmanager.backend.dto.StatusUpdateRequest;
import com.taskmanager.backend.dto.TaskRequest;
import com.taskmanager.backend.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    List<TaskResponse> getTasksByProject(Long projectId);

    List<TaskResponse> getMyTasks(String email);

    TaskResponse acceptTask(Long taskId,String email);

    TaskResponse updateTaskStatus(Long taskId,StatusUpdateRequest request,String email);
}