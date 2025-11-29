package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.UserJoinRequest;
import org.example.sports_gathering_system.Service.UserJoinRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-join")
@RequiredArgsConstructor
public class UserJoinRequestController {

    private final UserJoinRequestService userJoinRequestService;

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<?> getAllRequests(@PathVariable Integer adminId) {
        Integer check = userJoinRequestService.checkAdmin(adminId);
        if (check == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        if (check == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin."));

        return ResponseEntity.status(200).body(userJoinRequestService.getAllJoinRequests());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJoinRequest(@RequestBody @Valid UserJoinRequest request, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        Integer result = userJoinRequestService.addJoinRequest(request);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));
        if (result == -3)
            return ResponseEntity.status(400).body(new ApiResponse("You already submitted a join request."));

        return ResponseEntity.status(201).body(new ApiResponse("Join request submitted successfully."));
    }

    @PutMapping("/accept/id/{requestId}/user/{userId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer requestId, @PathVariable Integer userId) {
        Integer result = userJoinRequestService.acceptRequest(userId, requestId);
        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to accept this request."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Join request was not found."));
        if (result == -3)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Join request accepted successfully."));
    }

    @PutMapping("/reject/id/{requestId}/user/{userId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer requestId, @PathVariable Integer userId) {
        Integer result = userJoinRequestService.rejectRequest(userId, requestId);
        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to reject this request."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Join request was not found."));
        if (result == -3)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Join request rejected successfully."));
    }
}
