package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Model.UserActivity;
import org.example.sports_gathering_system.Model.UserParticipant;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserParticipantRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserParticipantService {

    private final UserParticipantRepository userParticipantRepository;
    private final UserActivityRepository userActivityRepository;
    private final UserRepository userRepository;

    public List<UserParticipant> getAllParticipants() {
        return userParticipantRepository.findAll();
    }

    public Integer addParticipant(Integer activityId, Integer leaderId, Integer participantId) {
        UserActivity activity = userActivityRepository.findUserActivityById(activityId);
        if (activity == null)
            return -1; //activity not found

        User participant = userRepository.findUserById(participantId);
        if (participant == null)
            return -2; //user not found

        if (participantId.equals(activity.getLeaderId()))
            return -3; //cannot join own activity

        if (!participant.getGender().equalsIgnoreCase(activity.getParticipantsGender()))
            return -4; //gender not same

        for (UserParticipant up : userParticipantRepository.findAll()) {
            if (up.getActivityId().equals(activityId) &&
                    up.getParticipantId().equals(participantId)) {
                return -5; //already joined
            }
        }

        long count = userParticipantRepository.findAll().stream()
                .filter(p -> p.getActivityId().equals(activityId))
                .count();

        if (count >= activity.getMaxParticipants())
            return -6; //activity full

        UserParticipant newP = new UserParticipant(null, activityId, leaderId, participantId);
        userParticipantRepository.save(newP);
        return 1;
    }

    public List<User> getParticipants(Integer activityId) {
        List<UserParticipant> participants = userParticipantRepository.findUserParticipantsByActivityId(activityId);
        if (participants.isEmpty())
            return null; // no participants

        //finding all participants ids
        List<Integer> ids = participants.stream()
                .map(UserParticipant::getParticipantId).toList();
        return userRepository.findAllById(ids);
    }

}