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

    @GetMapping("/get")
    public ResponseEntity<?> getAllCoaches() {
        return ResponseEntity.status(200).body(coachService.getAllCoaches());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCoach(@RequestBody @Valid Coach coach, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        coachService.addCoach(coach);
        return ResponseEntity.status(201).body(new ApiResponse("Coach added successfully"));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateCoach(@PathVariable Integer id, @PathVariable Integer userId,
                                         @RequestBody @Valid Coach coach,
                                         Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        return switch (coachService.updateCoach(userId, id, coach)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to update this coach"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("Coach was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Coach updated successfully"));
        };
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteCoach(@PathVariable Integer id, @PathVariable Integer userId) {
        return switch (coachService.deleteCoach(userId, id)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to delete this coach"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("Coach was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Coach deleted successfully"));
        };
    }

    @PutMapping("/approve/admin/{adminId}/coach/{coachId}")
    public ResponseEntity<?> approveCoach(@PathVariable Integer adminId, @PathVariable Integer coachId) {
        return switch (coachService.approveCoach(adminId, coachId)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("This user is not an admin"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("Coach was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Coach approved successfully"));
        };
    }

    @PutMapping("/reject/admin/{adminId}/coach/{coachId}")
    public ResponseEntity<?> rejectCoach(@PathVariable Integer adminId, @PathVariable Integer coachId) {
        return switch (coachService.rejectCoach(adminId, coachId)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("This user is not an admin"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("Coach was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Coach rejected successfully"));
        };
    }
}
