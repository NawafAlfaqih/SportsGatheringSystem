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
        Integer check = sportService.checkAdmin(adminId);
        if (check == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        if (check == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin."));
        return ResponseEntity.status(200).body(sportService.getAllSports());
    }

    @PostMapping("/add/admin/{adminId}")
    public ResponseEntity<?> addSport(@PathVariable Integer adminId, @RequestBody @Valid Sport sport, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        Integer result = sportService.addSport(adminId, sport);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        if (result == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin."));

        return ResponseEntity.status(201).body(new ApiResponse("Sport added successfully."));
    }

    @PutMapping("/update/id/{id}/admin/{adminId}")
    public ResponseEntity<?> updateSport(@PathVariable Integer id, @PathVariable Integer adminId,
                                         @RequestBody @Valid Sport sport, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        Integer result = sportService.updateSport(adminId, id, sport);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        if (result == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin."));
        if (result == -3)
            return ResponseEntity.status(404).body(new ApiResponse("Sport was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Sport updated successfully."));
    }

    @DeleteMapping("/delete/id/{id}/admin/{adminId}")
    public ResponseEntity<?> deleteSport(@PathVariable Integer id, @PathVariable Integer adminId) {
        Integer result = sportService.deleteSport(adminId, id);
        if (result == -1)
            return ResponseEntity.status(404).body(new ApiResponse("Admin was not found."));
        if (result == -2)
            return ResponseEntity.status(400).body(new ApiResponse("This user is not an admin."));
        if (result == -3)
            return ResponseEntity.status(404).body(new ApiResponse("Sport was not found."));

        return ResponseEntity.status(200).body(new ApiResponse("Sport deleted successfully."));
    }
}
