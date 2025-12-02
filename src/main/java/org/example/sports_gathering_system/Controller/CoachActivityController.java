package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.CoachActivity;
import org.example.sports_gathering_system.Service.CoachActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach-activity")
@RequiredArgsConstructor
public class CoachActivityController {

    private final CoachActivityService coachActivityService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllActivities() {
        return ResponseEntity.status(200).body(coachActivityService.getAllActivities());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addActivity(@RequestBody @Valid CoachActivity activity) {
        coachActivityService.addActivity(activity);
        return ResponseEntity.status(201).body(new ApiResponse("Activity added successfully."));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateActivity(@PathVariable Integer id, @PathVariable Integer userId,
                                            @RequestBody @Valid CoachActivity activity) {
        coachActivityService.updateActivity(userId, id, activity);
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, @PathVariable Integer userId) {
        coachActivityService.deleteActivity(userId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully."));
    }

    @GetMapping("/get/coach/{coachId}")
    public ResponseEntity<?> getActivitiesByCoach(@PathVariable Integer coachId) {
        return ResponseEntity.status(200).body(coachActivityService.getActivitiesByCoach(coachId));
    }

    @GetMapping("/get/sport/{sportId}")
    public ResponseEntity<?> getActivitiesBySport(@PathVariable Integer sportId) {
        return ResponseEntity.status(200).body(coachActivityService.getActivitiesBySport(sportId));
    }

    @GetMapping("/get/sorted/{order}")
    public ResponseEntity<?> getSorted(@PathVariable String order) {
        return ResponseEntity.status(200).body(coachActivityService.getSortedActivities(order));
    }
}
