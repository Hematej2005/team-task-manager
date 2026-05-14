package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.ProjectRequest;
import com.taskmanager.backend.dto.ProjectResponse;
import com.taskmanager.backend.entity.User;
import com.taskmanager.backend.enums.Role;
import com.taskmanager.backend.repository.UserRepository;
import com.taskmanager.backend.security.JwtUtil;
import com.taskmanager.backend.service.ProjectService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectRequest request,
            HttpServletRequest httpRequest
    ) {

        String token=null;

        Cookie[] cookies=httpRequest.getCookies();

        if(cookies!=null){

            for(Cookie cookie:cookies){

                if(cookie.getName().equals("jwt")){
                    token=cookie.getValue();
                }
            }
        }

        String email=jwtUtil.extractEmail(token);

        ProjectResponse response=projectService.createProject(request,email);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String,Object>>> getAllMembers() {

        List<User> members=userRepository.findByRole(Role.MEMBER);

        List<Map<String,Object>> responseList=new ArrayList<>();

        for(User user:members){

            Map<String,Object> userData=new HashMap<>();

            userData.put("id",user.getId());
            userData.put("name",user.getName());

            responseList.add(userData);
        }

        return ResponseEntity.ok(responseList);
    }
}