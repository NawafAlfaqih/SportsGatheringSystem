package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Service.UserActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-activity")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllActivities() {
        return ResponseEntity.status(200).body(userActivityService.getAllActivities());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addActivity(@RequestBody @Valid UserActivity activity) {
        userActivityService.addActivity(activity);
        return ResponseEntity.status(201).body(new ApiResponse("Activity added successfully."));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateActivity(@PathVariable Integer id, @PathVariable Integer userId,
                                            @RequestBody @Valid UserActivity activity) {
        userActivityService.updateActivity(userId, id, activity);
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, @PathVariable Integer userId) {
        userActivityService.deleteActivity(userId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully."));
    }

    @GetMapping("/get/by-city/user/{userId}")
    public ResponseEntity<?> getActivitiesByCity(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(userActivityService.getActivitiesByCity(userId));
    }

    @GetMapping("/get/by-sport/{sportId}")
    public ResponseEntity<?> getActivitiesBySport(@PathVariable Integer sportId) {
        return ResponseEntity.status(200).body(userActivityService.getActivitiesBySport(sportId));
    }

    @GetMapping("/get/upcoming")
    public ResponseEntity<?> getUpcomingActivities() {
        return ResponseEntity.status(200).body(userActivityService.getUpcomingActivities());
    }

    @GetMapping("/get/sorted/{order}")
    public ResponseEntity<?> getSorted(@PathVariable String order) {
        return ResponseEntity.status(200).body(userActivityService.getSorted(order));
    }

}
