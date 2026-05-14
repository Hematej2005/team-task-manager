package com.taskmanager.backend.serviceimpl;

import com.taskmanager.backend.dto.StatusUpdateRequest;
import com.taskmanager.backend.dto.TaskRequest;
import com.taskmanager.backend.dto.TaskResponse;
import com.taskmanager.backend.entity.Project;
import com.taskmanager.backend.entity.Task;
import com.taskmanager.backend.entity.User;
import com.taskmanager.backend.enums.TaskStatus;
import com.taskmanager.backend.repository.ProjectRepository;
import com.taskmanager.backend.repository.TaskRepository;
import com.taskmanager.backend.repository.UserRepository;
import com.taskmanager.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User member=userRepository.findById(request.getAssignedMemberId()).orElse(null);

        if(member==null){
            throw new RuntimeException("Member not found");
        }

        Project project=projectRepository.findById(request.getProjectId()).orElse(null);

        if(project==null){
            throw new RuntimeException("Project not found");
        }

        Task task=new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssignedMember(member);
        task.setDeadline(request.getDeadline());
        task.setProject(project);
        task.setStatus(TaskStatus.PENDING);

        Task savedTask=taskRepository.save(task);

        TaskResponse response=new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getAssignedMember().getName(),
                savedTask.getProject().getCreatedBy().getName(),
                savedTask.getProject().getProjectName(),
                savedTask.getDeadline(),
                savedTask.getStatus()
        );

        return response;
    }

    @Override
    public List<TaskResponse> getTasksByProject(Long projectId) {

        List<Task> allTasks=taskRepository.findAll();

        List<TaskResponse> responseList=new ArrayList<>();

        for(Task task:allTasks){

            if(task.getProject().getId().equals(projectId)){

                TaskResponse response=new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getAssignedMember().getName(),
                        task.getProject().getCreatedBy().getName(),
                        task.getProject().getProjectName(),
                        task.getDeadline(),
                        task.getStatus()
                );

                responseList.add(response);
            }
        }

        return responseList;
    }

    @Override
    public List<TaskResponse> getMyTasks(String email) {

        User member=userRepository.findByEmail(email).orElse(null);

        if(member==null){
            throw new RuntimeException("Member not found");
        }

        List<Task> allTasks=taskRepository.findAll();

        List<TaskResponse> responseList=new ArrayList<>();

        for(Task task:allTasks){

            if(task.getAssignedMember().getId().equals(member.getId())){

                TaskResponse response=new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getAssignedMember().getName(),
                        task.getProject().getCreatedBy().getName(),
                        task.getProject().getProjectName(),
                        task.getDeadline(),
                        task.getStatus()
                );

                responseList.add(response);
            }
        }

        return responseList;
    }

    @Override
    public TaskResponse acceptTask(Long taskId,String email) {

        Task task=taskRepository.findById(taskId).orElse(null);

        if(task==null){
            throw new RuntimeException("Task not found");
        }

        if(!task.getAssignedMember().getEmail().equals(email)){
            throw new RuntimeException("Unauthorized");
        }

        task.setStatus(TaskStatus.ACCEPTED);

        Task updatedTask=taskRepository.save(task);

        TaskResponse response=new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getAssignedMember().getName(),
                updatedTask.getProject().getCreatedBy().getName(),
                updatedTask.getProject().getProjectName(),
                updatedTask.getDeadline(),
                updatedTask.getStatus()
        );

        return response;
    }

    @Override
    public TaskResponse updateTaskStatus(Long taskId,StatusUpdateRequest request,String email) {

        Task task=taskRepository.findById(taskId).orElse(null);

        if(task==null){
            throw new RuntimeException("Task not found");
        }

        if(!task.getAssignedMember().getEmail().equals(email)){
            throw new RuntimeException("Unauthorized");
        }

        task.setStatus(request.getStatus());

        Task updatedTask=taskRepository.save(task);

        TaskResponse response=new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getAssignedMember().getName(),
                updatedTask.getProject().getCreatedBy().getName(),
                updatedTask.getProject().getProjectName(),
                updatedTask.getDeadline(),
                updatedTask.getStatus()
        );

        return response;
    }
}