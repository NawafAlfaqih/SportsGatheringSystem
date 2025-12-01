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
    public ResponseEntity<?> addActivity(@RequestBody @Valid CoachActivity activity, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        Integer result = coachActivityService.addActivity(activity);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Coach was not found."));
        if (result == -2)
            return ResponseEntity.status(400).body(new ApiResponse("Coach is not accepted."));
        return ResponseEntity.status(201).body(new ApiResponse("Activity added successfully."));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateActivity(@PathVariable Integer id, @PathVariable Integer userId,
                                            @RequestBody @Valid CoachActivity activity, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        Integer result = coachActivityService.updateActivity(userId, id, activity);
        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to update this activity."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id, @PathVariable Integer userId) {

        Integer result = coachActivityService.deleteActivity(userId, id);
        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to delete this activity."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully."));
    }

    @GetMapping("/get/coach/{coachId}")
    public ResponseEntity<?> getActivitiesByCoach(@PathVariable Integer coachId) {
        List<CoachActivity> list = coachActivityService.getActivitiesByCoach(coachId);
        if (list == null)
            return ResponseEntity.status(404).body(new ApiResponse("Coach was not found."));
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No activities found for this coach."));
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/sport/{sportId}")
    public ResponseEntity<?> getActivitiesBySport(@PathVariable Integer sportId) {
        List<CoachActivity> list = coachActivityService.getActivitiesBySport(sportId);
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No activities found for this sport."));
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/sorted/{order}")
    public ResponseEntity<?> getSorted(@PathVariable String order) {
        List<CoachActivity> list = coachActivityService.getSortedActivities(order);
        if (list == null)
            return ResponseEntity.status(400).body(new ApiResponse("Invalid order. Use 'asc' or 'desc'."));
        return ResponseEntity.status(200).body(list);
    }
}
