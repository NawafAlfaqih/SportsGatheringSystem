package org.example.sports_gathering_system.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Service.CoachParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coach-participant")
@RequiredArgsConstructor
public class CoachParticipantController {

    private final CoachParticipantService coachParticipantService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllParticipants() {
        return ResponseEntity.status(200).body(coachParticipantService.getAllParticipants());
    }
}
