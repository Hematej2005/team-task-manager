package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.StatusUpdateRequest;
import com.taskmanager.backend.dto.TaskRequest;
import com.taskmanager.backend.dto.TaskResponse;
import com.taskmanager.backend.security.JwtUtil;
import com.taskmanager.backend.service.TaskService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final JwtUtil jwtUtil;

    @PostMapping("/api/admin/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskRequest request
    ) {

        return ResponseEntity.ok(taskService.createTask(request));
    }

    @GetMapping("/api/admin/tasks/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/api/member/tasks")
    public ResponseEntity<List<TaskResponse>> getMyTasks(
            HttpServletRequest request
    ) {

        String email=getEmailFromCookie(request);

        return ResponseEntity.ok(taskService.getMyTasks(email));
    }

    @PutMapping("/api/member/tasks/{taskId}/accept")
    public ResponseEntity<TaskResponse> acceptTask(
            @PathVariable Long taskId,
            HttpServletRequest request
    ) {

        String email=getEmailFromCookie(request);

        return ResponseEntity.ok(taskService.acceptTask(taskId,email));
    }

    @PutMapping("/api/member/tasks/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody StatusUpdateRequest statusRequest,
            HttpServletRequest request
    ) {

        String email=getEmailFromCookie(request);

        return ResponseEntity.ok(
                taskService.updateTaskStatus(taskId,statusRequest,email)
        );
    }

    private String getEmailFromCookie(HttpServletRequest request){

        String token=null;

        Cookie[] cookies=request.getCookies();

        if(cookies!=null){

            for(Cookie cookie:cookies){

                if(cookie.getName().equals("jwt")){
                    token=cookie.getValue();
                }
            }
        }

        return jwtUtil.extractEmail(token);
    }
}