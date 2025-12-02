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
    public ResponseEntity<?> addRating(@RequestBody @Valid CoachRating rating) {
        coachRatingService.addRating(rating);
        return ResponseEntity.status(201).body(new ApiResponse("Rating added successfully."));
    }

    @PutMapping("/update/id/{id}/rater/{raterId}")
    public ResponseEntity<?> updateRating(@PathVariable Integer id, @PathVariable Integer raterId,
                                          @RequestBody @Valid CoachRating rating) {
        coachRatingService.updateRating(raterId, id, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/rater/{raterId}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id, @PathVariable Integer raterId) {
        coachRatingService.deleteRating(raterId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Rating deleted successfully."));
    }

}
