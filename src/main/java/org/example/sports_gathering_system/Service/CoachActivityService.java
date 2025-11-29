package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.Coach;
import org.example.sports_gathering_system.Model.CoachActivity;
import org.example.sports_gathering_system.Repository.CoachActivityRepository;
import org.example.sports_gathering_system.Repository.CoachRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachActivityService {

    private final CoachActivityRepository coachActivityRepository;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    public List<CoachActivity> getAllActivities() {
        return coachActivityRepository.findAll();
    }

    public Integer addActivity(CoachActivity activity) {
        Coach leader = coachRepository.findCoachById(activity.getCoachId());
        if (leader == null)
            return -1; //coach not found

        if (!"Accepted".equalsIgnoreCase(leader.getStatus()))
            return -2; //coach is not accepted

        activity.setParticipantIds("[]");

        coachActivityRepository.save(activity);
        return 1;
    }

    // ONLY leader OR admin can update
    public Integer updateActivity(Integer requesterId, Integer id, CoachActivity activity) {
        CoachActivity old = coachActivityRepository.findCoachActivityById(id);
        if (old == null)
            return -2; //activity not found

        boolean isLeader = requesterId.equals(old.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        old.setTitle(activity.getTitle());
        old.setDescription(activity.getDescription());
        old.setLocation(activity.getLocation());
        old.setParticipantsGender(activity.getParticipantsGender());
        old.setMaxParticipants(activity.getMaxParticipants());
        old.setSportId(activity.getSportId());
        old.setDateTime(activity.getDateTime());

        old.setParticipantIds(old.getParticipantIds());

        coachActivityRepository.save(old);
        return 1;
    }

    public Integer deleteActivity(Integer requesterId, Integer id) {
        CoachActivity activity = coachActivityRepository.findCoachActivityById(id);
        if (activity == null)
            return -2; //not found

        boolean isLeader = requesterId.equals(activity.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        coachActivityRepository.delete(activity);
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
