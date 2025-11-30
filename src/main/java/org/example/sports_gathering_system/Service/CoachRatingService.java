package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.Coach;
import org.example.sports_gathering_system.Model.CoachRating;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Repository.CoachRatingRepository;
import org.example.sports_gathering_system.Repository.CoachRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachRatingService {

    private final CoachRatingRepository coachRatingRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;

    public List<CoachRating> getAllRatings() {
        return coachRatingRepository.findAll();
    }

    public Integer addRating(CoachRating rating) {

        User rater = userRepository.findUserById(rating.getRaterId());
        if (rater == null)
            return -1; // rater not found

        Coach coach = coachRepository.findCoachById(rating.getCoachId());
        if (coach == null)
            return -2; // coach not found

        if (rating.getRaterId().equals(rating.getCoachId()))
            return -3; // cannot rate yourself (if IDs overlap)

        // prevent duplicate rating by same user on same coach
        for (CoachRating r : coachRatingRepository.findAll()) {
            if (r.getRaterId().equals(rating.getRaterId()) &&
                    r.getCoachId().equals(rating.getCoachId())) {
                return -4; // duplicate rating
            }
        }

        coachRatingRepository.save(rating);
        return 1;
    }

    public Integer updateRating(Integer raterId, Integer id, CoachRating rating) {

        CoachRating old = coachRatingRepository.findCoachRatingById(id);
        if (old == null)
            return -2; // rating not found

        if (!raterId.equals(old.getRaterId()))
            return -1; // unauthorized (must be owner)

        Coach coach = coachRepository.findCoachById(old.getCoachId());
        if (coach == null)
            return -3; // coach not found

        if (raterId.equals(old.getCoachId()))
            return -4; // cannot rate yourself

        old.setScore(rating.getScore());
        old.setComment(rating.getComment());

        coachRatingRepository.save(old);
        return 1;
    }

    public Integer deleteRating(Integer raterId, Integer id) {

        CoachRating rating = coachRatingRepository.findCoachRatingById(id);
        if (rating == null)
            return -2; // rating not found

        if (!raterId.equals(rating.getRaterId()))
            return -1; // unauthorized

        coachRatingRepository.delete(rating);
        return 1;
    }
}
