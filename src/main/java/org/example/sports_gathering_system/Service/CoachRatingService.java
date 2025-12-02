package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
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

    public void addRating(CoachRating rating) {

        User rater = userRepository.findUserById(rating.getRaterId());
        if (rater == null)
            throw new ApiException("Rater was not found."); //rater not found

        Coach coach = coachRepository.findCoachById(rating.getCoachId());
        if (coach == null)
            throw new ApiException("Coach was not found."); //coach not found

        // prevent duplicate rating by same user on same coach
        for (CoachRating r : coachRatingRepository.findAll()) {
            if (r.getRaterId().equals(rating.getRaterId()) &&
                    r.getCoachId().equals(rating.getCoachId())) {
                throw new ApiException("Duplicate rating is not allowed."); //duplicate rating
            }
        }
        coachRatingRepository.save(rating);
    }

    public void updateRating(Integer raterId, Integer id, CoachRating rating) {

        CoachRating old = coachRatingRepository.findCoachRatingById(id);
        if (old == null)
            throw new ApiException("Rating was not found."); //rating not found

        if (!raterId.equals(old.getRaterId()))
            throw new ApiException("You are not allowed to update this rating."); //unauthorized

        Coach coach = coachRepository.findCoachById(old.getCoachId());
        if (coach == null)
            throw new ApiException("Coach was not found."); //coach not found

        old.setScore(rating.getScore());
        old.setComment(rating.getComment());

        coachRatingRepository.save(old);
    }

    public void deleteRating(Integer raterId, Integer id) {
        CoachRating rating = coachRatingRepository.findCoachRatingById(id);
        if (rating == null)
            throw new ApiException("Rating was not found."); // rating not found

        if (!raterId.equals(rating.getRaterId()))
            throw new ApiException("You are not allowed to delete this rating."); // unauthorized

        coachRatingRepository.delete(rating);
    }
}
