package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Model.UserJoinRequest;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserJoinRequestRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserJoinRequestService {

    private final UserJoinRequestRepository userJoinRequestRepository;
    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;
    private final UserParticipantService userParticipantService;



    public List<UserJoinRequest> getAllJoinRequests(Integer adminId) { //admin only
        Integer check = checkAdmin(adminId);
        if (check == -1)
            throw new ApiException("Admin was not found.");
        if (check == -2)
            throw new ApiException("This user is not an admin.");

        return userJoinRequestRepository.findAll();
    }

    public void addJoinRequest(UserJoinRequest request) {
        if (userRepository.findUserById(request.getUserId()) == null)
            throw new ApiException("User was not found."); //user not found

        if (userActivityRepository.findUserActivityById(request.getActivityId()) == null)
            throw new ApiException("Activity was not found."); //activity not found

        // Check if already requested
        for (UserJoinRequest existing : userJoinRequestRepository.findAll()) {
            if (existing.getUserId().equals(request.getUserId()) &&
                    existing.getActivityId().equals(request.getActivityId())) {
                throw new ApiException("You already submitted a join request."); //already requested
            }
        }

        request.setStatus("Pending");
        userJoinRequestRepository.save(request);
    }

    public void acceptRequest(Integer requesterId, Integer requestId) {
        UserJoinRequest req = userJoinRequestRepository.findUserJoinRequestById(requestId);
        if (req == null)
            throw new ApiException("Join request was not found.");

        UserActivity activity = userActivityRepository.findUserActivityById(req.getActivityId());
        if (activity == null)
            throw new ApiException("Activity was not found.");

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            throw new ApiException("You are not allowed to accept this request.");

        // add participant
        Integer result = userParticipantService.addParticipant(
                req.getActivityId(),
                activity.getLeaderId(),
                req.getUserId()
        );

        if (result == -1)
            throw new ApiException("Activity was not found.");
        if (result == -2)
            throw new ApiException("Participant was not found.");
        if (result == -3)
            throw new ApiException("Cannot join own activity.");
        if (result == -4)
            throw new ApiException("Participant's gender is not allowed.");
        if (result == -5)
            throw new ApiException("Participant is already joined");
        if (result == -6)
            throw new ApiException("Activity is full");

        req.setStatus("Accepted");
        userJoinRequestRepository.save(req);
    }

    public void rejectRequest(Integer requesterId, Integer requestId) {
        UserJoinRequest req = userJoinRequestRepository.findUserJoinRequestById(requestId);
        if (req == null)
            throw new ApiException("Join request was not found."); //request not found

        UserActivity activity = userActivityRepository.findUserActivityById(req.getActivityId());
        if (activity == null)
            throw new ApiException("Activity was not found."); //activity not found

        boolean isLeader = requesterId.equals(activity.getLeaderId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isLeader && !isAdmin)
            throw new ApiException("You are not allowed to reject this request."); //unauthorized

        req.setStatus("Rejected");
        userJoinRequestRepository.save(req);
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //is admin
    }
}
