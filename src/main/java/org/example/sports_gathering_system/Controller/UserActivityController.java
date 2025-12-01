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
    public ResponseEntity<?> addActivity(@RequestBody @Valid UserActivity activity, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        Integer result = userActivityService.addActivity(activity);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));

        if (result == -2)
            return ResponseEntity.status(400).body(new ApiResponse("Admin cannot create an activity."));

        return ResponseEntity.status(201).body(new ApiResponse("Activity added successfully."));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateActivity(@PathVariable Integer id, @PathVariable Integer userId,
                                            @RequestBody @Valid UserActivity activity, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        Integer result = userActivityService.updateActivity(userId, id, activity);
        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to update this activity."));

        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, @PathVariable Integer userId) {

        Integer result = userActivityService.deleteActivity(userId, id);

        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to delete this activity."));

        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully."));
    }

    @GetMapping("/get/by-city/user/{userId}")
    public ResponseEntity<?> getActivitiesByCity(@PathVariable Integer userId) {
        List<UserActivity> list = userActivityService.getActivitiesByCity(userId);
        if (list == null)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No activities found in your city."));

        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/by-sport/{sportId}")
    public ResponseEntity<?> getActivitiesBySport(@PathVariable Integer sportId) {
        List<UserActivity> list = userActivityService.getActivitiesBySport(sportId);
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No activities found for this sport."));

        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/upcoming")
    public ResponseEntity<?> getUpcomingActivities() {
        List<UserActivity> list = userActivityService.getUpcomingActivities();
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No upcoming activities found."));
        return ResponseEntity.status(200).body(list);
    }

}
