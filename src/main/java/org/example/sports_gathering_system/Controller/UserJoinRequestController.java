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
        return ResponseEntity.status(200).body(userJoinRequestService.getAllJoinRequests(adminId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJoinRequest(@RequestBody @Valid UserJoinRequest request) {
        userJoinRequestService.addJoinRequest(request);
        return ResponseEntity.status(201).body(new ApiResponse("Join request submitted successfully."));
    }

    @PutMapping("/accept/id/{requestId}/user/{userId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer requestId, @PathVariable Integer userId) {
        userJoinRequestService.acceptRequest(userId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Join request accepted successfully."));
    }

    @PutMapping("/reject/id/{requestId}/user/{userId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer requestId, @PathVariable Integer userId) {
        userJoinRequestService.rejectRequest(userId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Join request rejected successfully."));
    }
}
