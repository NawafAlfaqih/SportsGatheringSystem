package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.Coach;
import org.example.sports_gathering_system.Service.CoachService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<?> getAllCoaches(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(coachService.getAllCoaches(adminId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCoach(@RequestBody @Valid Coach coach) {
        coachService.addCoach(coach);
        return ResponseEntity.status(201).body(new ApiResponse("Coach added successfully"));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateCoach(@PathVariable Integer id, @PathVariable Integer userId,
                                         @RequestBody @Valid Coach coach) {
        coachService.updateCoach(userId, id, coach);
        return ResponseEntity.status(200).body(new ApiResponse("Coach updated successfully"));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteCoach(@PathVariable Integer id, @PathVariable Integer userId) {
        coachService.deleteCoach(userId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Coach deleted successfully"));
    }

    @PutMapping("/approve/admin/{adminId}/coach/{coachId}")
    public ResponseEntity<?> approveCoach(@PathVariable Integer adminId, @PathVariable Integer coachId) {
        coachService.approveCoach(adminId, coachId);
        return ResponseEntity.status(200).body(new ApiResponse("Coach approved successfully"));
    }

    @PutMapping("/reject/admin/{adminId}/coach/{coachId}")
    public ResponseEntity<?> rejectCoach(@PathVariable Integer adminId, @PathVariable Integer coachId) {
        coachService.rejectCoach(adminId, coachId);
        return ResponseEntity.status(200).body(new ApiResponse("Coach rejected successfully"));
    }
}
