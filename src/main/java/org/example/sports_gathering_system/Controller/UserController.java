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
        Integer check = userService.checkAdmin(adminId);
        if (check == -1) {
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        }
        if (check == -2) {
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin"));
        }
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @PathVariable Integer userId,
                                        @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        return switch (userService.updateUser(userId, id, user)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to update this account"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("User was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        };
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id, @PathVariable Integer userId) {
        return switch (userService.deleteUser(userId, id)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to delete this account"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("User was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("User deleted successfully "));
        };
    }
}
