package com.taskmanager.backend.service;

import com.taskmanager.backend.dto.ProjectRequest;
import com.taskmanager.backend.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request,String email);

    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);
}