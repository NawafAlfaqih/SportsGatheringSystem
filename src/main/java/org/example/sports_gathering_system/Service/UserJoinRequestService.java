package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Model.UserJoinRequest;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserJoinRequestRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserJoinRequestService {

    private final UserJoinRequestRepository userJoinRequestRepository;
    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;


    public List<UserJoinRequest> getAllJoinRequests() { //admin only
        return userJoinRequestRepository.findAll();
    }

    public Integer addJoinRequest(UserJoinRequest request) {
        if (userRepository.findUserById(request.getUserId()) == null)
            return -1; //user not found

        if (userActivityRepository.findUserActivityById(request.getActivityId()) == null)
            return -2; //activity not found

        // Check if already requested
        for (UserJoinRequest existing : userJoinRequestRepository.findAll()) {
            if (existing.getUserId().equals(request.getUserId()) &&
                    existing.getActivityId().equals(request.getActivityId())) {
                return -3; //already requested
            }
        }

        request.setStatus("Pending");
        userJoinRequestRepository.save(request);
        return 1;
    }

    public Integer acceptRequest(Integer requesterId, Integer requestId) {
        UserJoinRequest req = userJoinRequestRepository.findUserJoinRequestById(requestId);
        if (req == null)
            return -2; //request not found

        UserActivity activity = userActivityRepository.findUserActivityById(req.getActivityId());
        if (activity == null)
            return -3; //activity not found

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        req.setStatus("Accepted");
        userJoinRequestRepository.save(req);
        return 1;
    }

    public Integer rejectRequest(Integer requesterId, Integer requestId) {
        UserJoinRequest req = userJoinRequestRepository.findUserJoinRequestById(requestId);
        if (req == null)
            return -2; //request not found

        UserActivity activity = userActivityRepository.findUserActivityById(req.getActivityId());
        if (activity == null)
            return -3; //activity not found

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        req.setStatus("Rejected");
        userJoinRequestRepository.save(req);
        return 1;
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //is admin
    }
}
