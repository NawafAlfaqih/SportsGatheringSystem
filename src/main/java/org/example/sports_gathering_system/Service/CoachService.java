package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Model.Coach;
import org.example.sports_gathering_system.Repository.CoachRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    public List<Coach> getAllCoaches(Integer adminId) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found.");

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin");

        return coachRepository.findAll();
    }

    public void addCoach(Coach coach) {
        coach.setStatus("Pending");
        coach.setCurrentActivityId(null);

        coachRepository.save(coach);
    }

    public void updateCoach(Integer requesterId, Integer id, Coach coach) {
        Coach oldCoach = coachRepository.findCoachById(id);
        if (oldCoach == null)
            throw new ApiException("Coach was not found"); //not found

        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isAdmin)
            throw new ApiException("Not allowed to update this coach"); //not allowed

        oldCoach.setTrainingSportId(coach.getTrainingSportId());
        oldCoach.setUsername(coach.getUsername());
        oldCoach.setPassword(coach.getPassword());
        oldCoach.setEmail(coach.getEmail());
        oldCoach.setPhoneNumber(coach.getPhoneNumber());
        oldCoach.setGender(coach.getGender());
        oldCoach.setAge(coach.getAge());
        oldCoach.setHeight(coach.getHeight());
        oldCoach.setWeight(coach.getWeight());
        oldCoach.setBio(coach.getBio());
        oldCoach.setCity(coach.getCity());
        oldCoach.setYearsOfExperience(coach.getYearsOfExperience());
        oldCoach.setCertificateLevel(coach.getCertificateLevel());

        oldCoach.setCurrentActivityId(oldCoach.getCurrentActivityId());
        oldCoach.setStatus(oldCoach.getStatus());

        coachRepository.save(oldCoach);
    }

    public void deleteCoach(Integer requesterId, Integer id) {
        Coach coach = coachRepository.findCoachById(id);
        if (coach == null)
            throw new ApiException("Coach was not found"); //not found

        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isAdmin)
            throw new ApiException("Not allowed to update this coach"); //not allowed

        coachRepository.delete(coach);
    }

    public void approveCoach(Integer adminId, Integer coachId) {
        if (checkAdmin(adminId) != 1)
            throw new ApiException("This user is not an admin"); //not admin

        Coach c = coachRepository.findCoachById(coachId);
        if (c == null)
            throw new ApiException("Coach was not found"); //not found

        c.setStatus("Accepted");
        coachRepository.save(c);
    }

    public void rejectCoach(Integer adminId, Integer coachId) {
        if (checkAdmin(adminId) != 1)
            throw new ApiException("This user is not an admin"); //not admin

        Coach c = coachRepository.findCoachById(coachId);
        if (c == null)
            throw new ApiException("Coach was not found"); //not found

        c.setStatus("Rejected");
        coachRepository.save(c);
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found
        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin
        return 1;
    }
}
