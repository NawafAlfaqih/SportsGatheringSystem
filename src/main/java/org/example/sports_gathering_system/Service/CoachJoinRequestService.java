package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Model.*;
import org.example.sports_gathering_system.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachJoinRequestService {

    private final CoachJoinRequestRepository coachJoinRequestRepository;
    private final CoachActivityRepository coachActivityRepository;
    private final UserRepository userRepository;
    private final CoachParticipantService coachParticipantService;

    public List<CoachJoinRequest> getAllJoinRequests(Integer adminId) { // admin only
        Integer check = checkAdmin(adminId);
        if (check == -1)
            throw new ApiException("Admin was not found.");
        if (check == -2)
            throw new ApiException("This user is not an admin.");

        return coachJoinRequestRepository.findAll();
    }
    public Integer addJoinRequest(CoachJoinRequest request) {
        User user = userRepository.findUserById(request.getUserId());
        if (user == null)
            return -1; // user not found

        CoachActivity activity = coachActivityRepository.findCoachActivityById(request.getActivityId());
        if (activity == null)
            return -2; // activity not found

        // prevent duplicate requests
        for (CoachJoinRequest existing : coachJoinRequestRepository.findAll()) {
            if (existing.getUserId().equals(request.getUserId()) &&
                    existing.getActivityId().equals(request.getActivityId())) {
                return -3; // already requested
            }
        }

        request.setStatus("Pending");
        coachJoinRequestRepository.save(request);
        return 1;
    }

        public Integer acceptRequest(Integer requesterId, Integer requestId) {
        CoachJoinRequest req = coachJoinRequestRepository.findCoachJoinRequestById(requestId);
        if (req == null)
            return -2; // request not found

        CoachActivity activity = coachActivityRepository.findCoachActivityById(req.getActivityId());
        if (activity == null)
            return -3; // activity not found

        boolean isLeader = requesterId.equals(activity.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            return -1; // unauthorized

        Integer result = coachParticipantService.addParticipant(
                req.getActivityId(),
                activity.getCoachId(),
                req.getUserId()
        );

        if (result == -1)
            throw new ApiException("Activity was not found.");
        if (result == -2)
            throw new ApiException("Participant was not found.");
        if (result == -3)
            throw new ApiException("Participant's gender is not allowed.");
        if (result == -4)
            throw new ApiException("Participant is already joined");
        if (result == -5)
            throw new ApiException("Activity is full");

        req.setStatus("Accepted");
        coachJoinRequestRepository.save(req);
        return 1;
    }

    public Integer rejectRequest(Integer requesterId, Integer requestId) {
        CoachJoinRequest req = coachJoinRequestRepository.findCoachJoinRequestById(requestId);
        if (req == null)
            return -2; //request not found

        CoachActivity activity = coachActivityRepository.findCoachActivityById(req.getActivityId());
        if (activity == null)
            return -3; //activity not found

        boolean isLeader = requesterId.equals(activity.getCoachId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);

        if (!isLeader && !isAdmin)
            return -1; //unauthorized

        req.setStatus("Rejected");
        coachJoinRequestRepository.save(req);
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
