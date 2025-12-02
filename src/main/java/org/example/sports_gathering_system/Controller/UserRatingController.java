package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.UserRating;
import org.example.sports_gathering_system.Service.UserRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-rating")
@RequiredArgsConstructor
public class UserRatingController {

    private final UserRatingService userRatingService;

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<?> getAllRatings(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(userRatingService.getAllRatings(adminId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody @Valid UserRating rating) {
        userRatingService.addRating(rating);
        return ResponseEntity.status(201).body(new ApiResponse("Rating added successfully"));
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateRating(@PathVariable Integer id, @PathVariable Integer userId,
                                          @RequestBody @Valid UserRating rating) {
        userRatingService.updateRating(userId, id, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully"));
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id, @PathVariable Integer userId) {
        userRatingService.deleteRating(userId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully"));
    }

    @GetMapping("/get/rater/{raterId}")
    public ResponseEntity<?> getRatingsByRater(@PathVariable Integer raterId) {
        return ResponseEntity.status(200).body(userRatingService.getRatingsByRater(raterId));
    }

    @GetMapping("/get/target/{targetUserId}")
    public ResponseEntity<?> getRatingsByTarget(@PathVariable Integer targetUserId) {
        return ResponseEntity.status(200).body(userRatingService.getRatingsByTarget(targetUserId));
    }

    @GetMapping("/get/avg/{targetUserId}")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer targetUserId) {
       return ResponseEntity.status(200).body(userRatingService.getAverageRating(targetUserId));
    }

    @GetMapping("/get/activity/{activityId}")
    public ResponseEntity<?> getRatingsByActivity(@PathVariable Integer activityId) {
       return ResponseEntity.status(200).body(userRatingService.getRatingsByActivity(activityId));
    }
}
