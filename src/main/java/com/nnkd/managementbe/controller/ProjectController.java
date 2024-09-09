package com.nnkd.managementbe.controller;

import com.nnkd.managementbe.dto.request.ApiResponse;
import com.nnkd.managementbe.model.User;
import com.nnkd.managementbe.service.AuthenticationService;
import com.nnkd.managementbe.service.ProjectService;
import com.nnkd.managementbe.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    UserService userService;
    ProjectService projectService;
    AuthenticationService authenticationService;

    @GetMapping
    public ApiResponse getProjects() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(projectService.getProjects());
        return apiResponse;
    }

    @GetMapping("/projectsOfUser")
    public ApiResponse getProjectsOfUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            boolean isValid = authenticationService.verifyToken(token);
            if (isValid) {
                ApiResponse apiResponse = new ApiResponse();
                String email = authenticationService.getEmailFromextractClaims(token);
                User user = userService.getUser(email);
                ObjectId id = new ObjectId(user.getId());
                apiResponse.setResult(projectService.getProjectsHasUser(id));
                return apiResponse;
            } else {
                throw new RuntimeException("Invalid token");
            }
        } else {
            throw new RuntimeException("Authorization header is missing or malformed");
        }
    }
}