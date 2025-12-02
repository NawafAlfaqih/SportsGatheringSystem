package org.example.sports_gathering_system.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Service.UserParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-participant")
@RequiredArgsConstructor
public class UserParticipantController {

    private final UserParticipantService userParticipantService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllParticipants() {
        return ResponseEntity.status(200).body(userParticipantService.getAllParticipants());
    }

    @GetMapping("/participants/{activityId}")
    public ResponseEntity<?> getParticipants(@PathVariable Integer activityId) {
        return ResponseEntity.status(200).body(userParticipantService.getParticipants(activityId));
    }



}
