package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
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

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public void addCoach(Coach coach) {
        coach.setStatus("Pending");
        coach.setCurrentActivityId(null);

        coachRepository.save(coach);
    }

    public Integer updateCoach(Integer requesterId, Integer id, Coach coach) {
        Coach oldCoach = coachRepository.findCoachById(id);
        if (oldCoach == null)
            return -2; //not found

        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isAdmin)
            return -1; //not allowed

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
        return 1;
    }

    public Integer deleteCoach(Integer requesterId, Integer id) {
        Coach coach = coachRepository.findCoachById(id);
        if (coach == null)
            return -2; //not found

        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isAdmin)
            return -1; //not admin

        coachRepository.delete(coach);
        return 1;
    }

    public Integer approveCoach(Integer adminId, Integer coachId) {
        if (checkAdmin(adminId) != 1)
            return -1; //not admin

        Coach c = coachRepository.findCoachById(coachId);
        if (c == null)
            return -2; //not found

        c.setStatus("Accepted");
        coachRepository.save(c);
        return 1;
    }

    public Integer rejectCoach(Integer adminId, Integer coachId) {
        if (checkAdmin(adminId) != 1)
            return -1; //not admin

        Coach c = coachRepository.findCoachById(coachId);
        if (c == null)
            return -2;

        c.setStatus("Rejected");
        coachRepository.save(c);
        return 1;
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found
        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin
        return 1;
    }
}
