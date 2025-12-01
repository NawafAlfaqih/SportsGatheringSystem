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
        Integer check = userRatingService.checkAdmin(adminId);
        if (check == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found"));
        if (check == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin"));

        return ResponseEntity.status(200).body(userRatingService.getAllRatings());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody @Valid UserRating rating, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }
        return switch (userRatingService.addRating(rating)) {
            case -1 -> ResponseEntity.status(404).body(new ApiResponse("User was not found"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("You cannot rate yourself"));
            case -3 -> ResponseEntity.status(400).body(new ApiResponse("This rating already exists"));
            default -> ResponseEntity.status(201).body(new ApiResponse("Rating added successfully"));
        };
    }

    @PutMapping("/update/id/{id}/user/{userId}")
    public ResponseEntity<?> updateRating(@PathVariable Integer id, @PathVariable Integer userId,
                                          @RequestBody @Valid UserRating rating, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }
        return switch (userRatingService.updateRating(userId, id, rating)) {
            case -1 -> ResponseEntity.status(404).body(new ApiResponse("Rating was not found"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to update this rating"));
            case -3 -> ResponseEntity.status(400).body(new ApiResponse("Duplicate rating exists"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully"));
        };
    }

    @DeleteMapping("/delete/id/{id}/user/{userId}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id, @PathVariable Integer userId) {
        return switch (userRatingService.deleteRating(userId, id)) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Not allowed to delete this rating"));
            case -2 -> ResponseEntity.status(404).body(new ApiResponse("Rating was not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully"));
        };
    }

    @GetMapping("/get/rater/{raterId}")
    public ResponseEntity<?> getRatingsByRater(@PathVariable Integer raterId) {
        List<UserRating> list = userRatingService.getRatingsByRater(raterId);
        if (list == null)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No ratings found."));
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/target/{targetUserId}")
    public ResponseEntity<?> getRatingsByTarget(@PathVariable Integer targetUserId) {
        List<UserRating> list = userRatingService.getRatingsByTarget(targetUserId);
        if (list == null)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No ratings found."));
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/get/avg/{targetUserId}")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer targetUserId) {
        Double avg = userRatingService.getAverageRating(targetUserId);
        if (avg == null)
            return ResponseEntity.status(404).body(new ApiResponse("User was not found."));
        return ResponseEntity.status(200).body(avg);
    }

    @GetMapping("/get/activity/{activityId}")
    public ResponseEntity<?> getRatingsByActivity(@PathVariable Integer activityId) {
        List<UserRating> list = userRatingService.getRatingsByActivity(activityId);
        if (list == null)
            return ResponseEntity.status(404).body(new ApiResponse("Activity was not found."));
        if (list.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("No ratings found."));
        return ResponseEntity.status(200).body(list);
    }
}
