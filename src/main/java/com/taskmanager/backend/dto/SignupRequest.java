package com.taskmanager.backend.dto;

import com.taskmanager.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String name;

    private String email;

    private String password;

    private Role role;
}