package com.zjgsu.chr.user.controller;

import com.zjgsu.chr.user.model.User;
import com.zjgsu.chr.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.create(request.name(), request.phone());
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(toResponse(user));
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return toResponse(userService.findById(id));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getPhone());
    }

    public record CreateUserRequest(@NotBlank String name, @NotBlank String phone) { }
    public record UserResponse(Long id, String name, String phone) { }
}
