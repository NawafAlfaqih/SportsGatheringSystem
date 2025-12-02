package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
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

    public void addActivity(CoachActivity activity) {
        Coach leader = coachRepository.findCoachById(activity.getCoachId());
        if (leader == null)
            throw new ApiException("Coach was not found."); //coach not found

        if (!"Accepted".equalsIgnoreCase(leader.getStatus()))
            throw new ApiException("Coach is not accepted."); //coach is not accepted

        activity.setParticipantIds("[]");

        coachActivityRepository.save(activity);
    }

    // ONLY leader OR admin can update
    public void updateActivity(Integer requesterId, Integer id, CoachActivity activity) {
        CoachActivity old = coachActivityRepository.findCoachActivityById(id);
        if (old == null)
            throw new ApiException("Activity was not found."); //activity not found

        boolean isLeader = requesterId.equals(old.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            throw new ApiException("You are not allowed to update this activity."); //unauthorized

        old.setTitle(activity.getTitle());
        old.setDescription(activity.getDescription());
        old.setLocation(activity.getLocation());
        old.setParticipantsGender(activity.getParticipantsGender());
        old.setMaxParticipants(activity.getMaxParticipants());
        old.setSportId(activity.getSportId());
        old.setDateTime(activity.getDateTime());

        old.setParticipantIds(old.getParticipantIds());

        coachActivityRepository.save(old);
    }

    public void deleteActivity(Integer requesterId, Integer id) {
        CoachActivity activity = coachActivityRepository.findCoachActivityById(id);
        if (activity == null)
            throw new ApiException("Activity was not found."); //not found

        boolean isLeader = requesterId.equals(activity.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            throw new ApiException("You are not allowed to delete this activity."); //unauthorized

        coachActivityRepository.delete(activity);
    }

    public List<CoachActivity> getActivitiesByCoach(Integer coachId) {
        if (coachRepository.findCoachById(coachId) == null)
            throw new ApiException("No activities found for this sport."); //coach not found

        List<CoachActivity> list = coachActivityRepository.findCoachActivitiesByCoachId(coachId);
        if (list.isEmpty())
            throw new ApiException("No activities found for this coach.");

        return list;
    }

    public List<CoachActivity> getActivitiesBySport(Integer sportId) {
        List<CoachActivity> list = coachActivityRepository.findCoachActivitiesBySportId(sportId);
        if (list.isEmpty())
            throw new ApiException("No activities found for this sport.");

        return list ;
    }

    public List<CoachActivity> getSortedActivities(String order) {
        if (order.equalsIgnoreCase("asc"))
            return coachActivityRepository.sortAsc();
        else if (order.equalsIgnoreCase("desc"))
            return coachActivityRepository.sortDesc();
        throw new ApiException("Invalid order. Use 'asc' or 'desc'."); //invalid input
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1;
    }
}
