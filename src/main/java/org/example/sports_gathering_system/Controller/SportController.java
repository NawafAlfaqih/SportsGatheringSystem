package org.example.sports_gathering_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.Sport;
import org.example.sports_gathering_system.Service.SportService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sport")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<?> getAllSports(@PathVariable Integer adminId) {
        return ResponseEntity.status(200).body(sportService.getAllSports(adminId));
    }

    @PostMapping("/add/admin/{adminId}")
    public ResponseEntity<?> addSport(@PathVariable Integer adminId, @RequestBody @Valid Sport sport) {
        sportService.addSport(adminId, sport);
        return ResponseEntity.status(201).body(new ApiResponse("Sport added successfully."));
    }

    @PutMapping("/update/id/{id}/admin/{adminId}")
    public ResponseEntity<?> updateSport(@PathVariable Integer id, @PathVariable Integer adminId,
                                         @RequestBody @Valid Sport sport) {
        sportService.updateSport(adminId, id, sport);

        return ResponseEntity.status(200).body(new ApiResponse("Sport updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/admin/{adminId}")
    public ResponseEntity<?> deleteSport(@PathVariable Integer id, @PathVariable Integer adminId) {
        sportService.deleteSport(adminId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Sport deleted successfully."));
    }
}
