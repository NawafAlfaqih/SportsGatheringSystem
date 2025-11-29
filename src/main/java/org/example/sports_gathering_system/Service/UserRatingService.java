package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.UserRating;
import org.example.sports_gathering_system.Repository.UserRatingRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;

    public List<UserRating> getAllRatings() { //admin only
        return userRatingRepository.findAll();
    }

    public Integer addRating(UserRating rating) {
        if (userRepository.findUserById(rating.getRaterId()) == null)
            return -1; //rater not found

        if (userRepository.findUserById(rating.getTargetUserId()) == null)
            return -2; //target not found

        if (rating.getRaterId().equals(rating.getTargetUserId()))
            return -3; //cannot rate yourself

        UserRating existing = userRatingRepository
                .findUserRatingByRaterIdAndTargetUserIdAndActivityId(
                        rating.getRaterId(),
                        rating.getTargetUserId(),
                        rating.getActivityId()
                );

        if (existing != null)
            return -4; // duplicate rating

        rating.setDate(LocalDateTime.now());
        userRatingRepository.save(rating);
        return 1;
    }

    public Integer updateRating(Integer requesterId, Integer id, UserRating rating) {
        UserRating old = userRatingRepository.findUserRatingById(id);
        if (old == null)
            return -1; //rating not found

        boolean isRater = requesterId.equals(old.getRaterId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isRater && !isAdmin)
            return -2; //unauthorized

        UserRating existing = userRatingRepository
                .findUserRatingByRaterIdAndTargetUserIdAndActivityId(
                        old.getRaterId(),
                        rating.getTargetUserId(),
                        rating.getActivityId()
                );
        if (existing != null)
            return -3; //duplicate rating

        old.setTargetUserId(rating.getTargetUserId());
        old.setActivityId(rating.getActivityId());
        old.setScore(rating.getScore());
        old.setComment(rating.getComment());
        old.setDate(LocalDateTime.now());

        userRatingRepository.save(old);
        return 1;
    }

    public Integer deleteRating(Integer requesterId, Integer id) {
        UserRating rating = userRatingRepository.findUserRatingById(id);
        if (rating == null)
            return -2; //not found

        boolean isRater = requesterId.equals(rating.getRaterId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isRater && !isAdmin)
            return -1; //unauthorized

        userRatingRepository.delete(rating);
        return 1;
    }



    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1;
    }

    public List<UserRating> getRatingsForUser(Integer userId) {
        return userRatingRepository.findUserRatingsByTargetUserId(userId);
    }
}
