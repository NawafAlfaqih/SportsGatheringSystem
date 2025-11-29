package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.Coach;
import org.example.sports_gathering_system.Model.CoachActivity;
import org.example.sports_gathering_system.Model.CoachJoinRequest;
import org.example.sports_gathering_system.Repository.CoachActivityRepository;
import org.example.sports_gathering_system.Repository.CoachJoinRequestRepository;
import org.example.sports_gathering_system.Repository.CoachRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachJoinRequestService {

    private final CoachJoinRequestRepository coachJoinRequestRepository;
    private final CoachRepository coachRepository;
    private final CoachActivityRepository coachActivityRepository;
    private final UserRepository userRepository;

    public List<CoachJoinRequest> getAllJoinRequests() { // admin only
        return coachJoinRequestRepository.findAll();
    }

    public Integer addJoinRequest(CoachJoinRequest request) {
        Coach coach = coachRepository.findCoachById(request.getCoachId());
        if (coach == null)
            return -1; //coach not found

        if (!"Accepted".equalsIgnoreCase(coach.getStatus()))
            return -4; //coach not accepted

        CoachActivity activity = coachActivityRepository.findCoachActivityById(request.getActivityId());
        if (activity == null)
            return -2; //activity not found

        // Check duplicate
        for (CoachJoinRequest existing : coachJoinRequestRepository.findAll()) {
            if (existing.getCoachId().equals(request.getCoachId()) &&
                    existing.getActivityId().equals(request.getActivityId())) {
                return -3; //already requested
            }
        }

        request.setStatus("Pending");
        coachJoinRequestRepository.save(request);
        return 1;
    }

    public Integer acceptRequest(Integer requesterId, Integer requestId) {
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
