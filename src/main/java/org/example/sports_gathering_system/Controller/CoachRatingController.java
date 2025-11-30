package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.CoachRating;
import org.example.sports_gathering_system.Service.CoachRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coach-rating")
@RequiredArgsConstructor
public class CoachRatingController {

    private final CoachRatingService coachRatingService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRatings() {
        return ResponseEntity.status(200).body(coachRatingService.getAllRatings());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@RequestBody @Valid CoachRating rating, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        Integer result = coachRatingService.addRating(rating);

        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Rater was not found."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Coach was not found."));
        if (result == -3)
            return ResponseEntity.status(400).body(new ApiResponse("Cannot rate yourself."));
        if (result == -4)
            return ResponseEntity.status(400).body(new ApiResponse("Duplicate rating is not allowed."));

        return ResponseEntity.status(201).body(new ApiResponse("Rating added successfully."));
    }

    @PutMapping("/update/id/{id}/rater/{raterId}")
    public ResponseEntity<?> updateRating(@PathVariable Integer id, @PathVariable Integer raterId,
                                          @RequestBody @Valid CoachRating rating, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        Integer result = coachRatingService.updateRating(raterId, id, rating);

        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to update this rating."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Rating was not found."));
        if (result == -3)
            return ResponseEntity.status(404).body(new ApiResponse("Coach was not found."));
        if (result == -4)
            return ResponseEntity.status(400).body(new ApiResponse("Cannot rate yourself."));

        return ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/rater/{raterId}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id, @PathVariable Integer raterId) {

        Integer result = coachRatingService.deleteRating(raterId, id);

        if (result == -1)
            return ResponseEntity.status(400).body(new ApiResponse("You are not allowed to delete this rating."));
        if (result == -2)
            return ResponseEntity.status(404).body(new ApiResponse("Rating was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully."));
    }

}
