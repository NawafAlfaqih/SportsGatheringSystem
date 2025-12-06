package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<?> getAllUsers(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(userService.getAllUsers(adminId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @PathVariable Integer userId,
                                        @RequestBody @Valid User user) {
        userService.updateUser(userId, id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id, @PathVariable Integer userId) {
        userService.deleteUser(userId, id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully "));
    }

    @GetMapping("/ask-ai/user-id/{userId}")
    public ResponseEntity<?> askAi(@PathVariable Integer userId, @RequestBody String prompt) {
        return ResponseEntity.status(200).body(userService.askAi(userId,prompt));
    }
}
