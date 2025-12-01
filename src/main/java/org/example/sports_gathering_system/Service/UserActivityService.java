package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;
    private final UserRepository userRepository;

    public List<UserActivity> getAllActivities() {
        return userActivityRepository.findAll();
    }

    public Integer addActivity(UserActivity activity) {
        if (userRepository.findUserById(activity.getLeaderId()) == null)
            return -1; // user not found

        if (checkAdmin(activity.getLeaderId()) == 1)
            return -2; // admin not allowed

        activity.setParticipantIds("[]");
        userActivityRepository.save(activity);
        return 1;
    }


    //ONLY leader OR admin can update
    public Integer updateActivity(Integer requesterId, Integer id, UserActivity activity) {
        UserActivity old = userActivityRepository.findUserActivityById(id);
        if (old == null)
            return -2; //activity not found

        boolean isLeader = requesterId.equals(old.getLeaderId());
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

        userActivityRepository.save(old);
        return 1;
    }

    public Integer deleteActivity(Integer requesterId, Integer id) {
        UserActivity activity = userActivityRepository.findUserActivityById(id);
        if (activity == null)
            return -2; //not found

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        userActivityRepository.delete(activity);
        return 1;
    }

    public List<UserActivity> getActivitiesByCity(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            return null; //user not found

        return userActivityRepository.findActivitiesByCity(user.getCity());
    }

    public List<UserActivity> getActivitiesBySport(Integer sportId) {
        return userActivityRepository.findUserActivitiesBySportId(sportId);
    }

    public List<UserActivity> getUpcomingActivities() {
        return userActivityRepository.findUpcomingActivities();
    }


    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //is admin
    }
}
