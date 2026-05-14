package com.taskmanager.backend.serviceimpl;

import com.taskmanager.backend.dto.ProjectRequest;
import com.taskmanager.backend.dto.ProjectResponse;
import com.taskmanager.backend.entity.Project;
import com.taskmanager.backend.entity.User;
import com.taskmanager.backend.repository.ProjectRepository;
import com.taskmanager.backend.repository.UserRepository;
import com.taskmanager.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request,String email) {

        User user=userRepository.findByEmail(email).orElse(null);

        if(user==null){
            throw new RuntimeException("User not found");
        }

        Project project=new Project();

        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setCreatedBy(user);

        Project savedProject=projectRepository.save(project);

        ProjectResponse response=new ProjectResponse(
                savedProject.getId(),
                savedProject.getProjectName(),
                savedProject.getDescription(),
                savedProject.getCreatedBy().getName()
        );

        return response;
    }

    @Override
    public List<ProjectResponse> getAllProjects() {

        List<Project> projects=projectRepository.findAll();

        List<ProjectResponse> responseList=new ArrayList<>();

        for(Project project:projects){

            ProjectResponse response=new ProjectResponse(
                    project.getId(),
                    project.getProjectName(),
                    project.getDescription(),
                    project.getCreatedBy().getName()
            );

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ProjectResponse getProjectById(Long id) {

        Project project=projectRepository.findById(id).orElse(null);

        if(project==null){
            throw new RuntimeException("Project not found");
        }

        ProjectResponse response=new ProjectResponse(
                project.getId(),
                project.getProjectName(),
                project.getDescription(),
                project.getCreatedBy().getName()
        );

        return response;
    }
}