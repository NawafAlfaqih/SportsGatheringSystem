package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

    public void addActivity(UserActivity activity) {
        if (userRepository.findUserById(activity.getLeaderId()) == null)
            throw new ApiException("User was not found."); // user not found

        if (checkAdmin(activity.getLeaderId()) == 1)
            throw new ApiException("Admin cannot create an activity."); // admin not allowed

        activity.setParticipantIds("[]");
        userActivityRepository.save(activity);
    }


    //ONLY leader OR admin can update
    public void updateActivity(Integer requesterId, Integer id, UserActivity activity) {
        UserActivity old = userActivityRepository.findUserActivityById(id);
        if (old == null)
            throw new ApiException("Activity was not found."); //activity not found

        boolean isLeader = requesterId.equals(old.getLeaderId());
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

        userActivityRepository.save(old);
   }

    public void deleteActivity(Integer requesterId, Integer id) {
        UserActivity activity = userActivityRepository.findUserActivityById(id);
        if (activity == null)
            throw new ApiException("Activity was not found."); //not found

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isLeader && !isAdmin)
            throw new ApiException("You are not allowed to delete this activity."); //unauthorized

        userActivityRepository.delete(activity);
    }

    public List<UserActivity> getActivitiesByCity(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User was not found."); //user not found

        List<UserActivity> userActivities =
                userActivityRepository.findActivitiesByCity(user.getCity());

        if (userActivities.isEmpty())
            throw new ApiException("No activities found in your city."); //no activities

        return userActivities;
    }

    public List<UserActivity> getActivitiesBySport(Integer sportId) {
        List<UserActivity> list = userActivityRepository.findUserActivitiesBySportId(sportId);
        if (list.isEmpty())
            throw new ApiException("No activities found for this sport."); //no activities

        return list;
    }

    public List<UserActivity> getUpcomingActivities() {
        List<UserActivity> list = userActivityRepository.findUpcomingActivities();
        if (list.isEmpty())
            throw new ApiException("No upcoming activities found."); //no activities

        return list;
    }

    public List<UserActivity> getSorted(String order) {
        if (order.equalsIgnoreCase("asc"))
            return userActivityRepository.sortAsc();
        else if (order.equalsIgnoreCase("desc"))
            return userActivityRepository.sortDesc();
        throw new ApiException("Invalid order. Use 'asc' or 'desc'."); //invalid input
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //is admin
    }
}
