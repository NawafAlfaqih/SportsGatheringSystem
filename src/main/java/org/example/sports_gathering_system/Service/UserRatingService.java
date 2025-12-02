package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Api.ApiResponse;
import org.example.sports_gathering_system.Model.UserRating;
import org.example.sports_gathering_system.Repository.UserActivityRepository;
import org.example.sports_gathering_system.Repository.UserRatingRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserActivityRepository userActivityRepository;
    private final UserRepository userRepository;

    public List<UserRating> getAllRatings(Integer adminId) { //admin only
        Integer check = checkAdmin(adminId);
        if (check == -1)
            throw new ApiException("Admin was not found");
        if (check == -2)
            throw new ApiException("This user is not an admin");

        return userRatingRepository.findAll();
    }

    public void addRating(UserRating rating) {
        if (userRepository.findUserById(rating.getRaterId()) == null)
            throw new ApiException("User was not found"); //rater not found

        if (userRepository.findUserById(rating.getTargetUserId()) == null)
            throw new ApiException("Rated User was not found"); //target not found

        if (rating.getRaterId().equals(rating.getTargetUserId()))
            throw new ApiException("Cannot Rate yourself"); //cannot rate yourself

        UserRating existing = userRatingRepository
                .findUserRatingByRaterIdAndTargetUserIdAndActivityId(
                        rating.getRaterId(),
                        rating.getTargetUserId(),
                        rating.getActivityId()
                );

        if (existing != null)
            throw new ApiException("Duplicate rating exists"); //duplicate rating

        rating.setDate(LocalDateTime.now());
        userRatingRepository.save(rating);
    }

    public void updateRating(Integer requesterId, Integer id, UserRating rating) {
        UserRating old = userRatingRepository.findUserRatingById(id);
        if (old == null)
            throw new ApiException("Rating was not found"); //rating not found

        boolean isRater = requesterId.equals(old.getRaterId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isRater && !isAdmin)
            throw new ApiException("Not allowed to update this rating"); //unauthorized

        UserRating existing = userRatingRepository
                .findUserRatingByRaterIdAndTargetUserIdAndActivityId(
                        old.getRaterId(),
                        rating.getTargetUserId(),
                        rating.getActivityId()
                );
        if (existing != null)
            throw new ApiException("Duplicate rating exists"); //duplicate rating

        old.setTargetUserId(rating.getTargetUserId());
        old.setActivityId(rating.getActivityId());
        old.setScore(rating.getScore());
        old.setComment(rating.getComment());
        old.setDate(LocalDateTime.now());

        userRatingRepository.save(old);
    }

    public void deleteRating(Integer requesterId, Integer id) {
        UserRating rating = userRatingRepository.findUserRatingById(id);
        if (rating == null)
            throw new ApiException("Rating was not found"); //not found

        boolean isRater = requesterId.equals(rating.getRaterId());
        boolean isAdmin = (checkAdmin(requesterId) == 1);
        if (!isRater && !isAdmin)
            throw new ApiException("Not allowed to delete this rating"); //unauthorized

        userRatingRepository.delete(rating);
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

    public List<UserRating> getRatingsByRater(Integer raterId) {
        if (userRepository.findUserById(raterId) == null)
            throw new ApiException("User was not found."); //not found
        return userRatingRepository.findUserRatingsByRaterId(raterId);
    }

    public List<UserRating> getRatingsByTarget(Integer targetUserId) {
        if (userRepository.findUserById(targetUserId) == null)
            throw new ApiException("User was not found."); //not found
        return userRatingRepository.findUserRatingsByTargetUserId(targetUserId);
    }

    public List<UserRating> getRatingsByActivity(Integer activityId) {
        if (userActivityRepository.findUserActivityById(activityId) == null)
            throw new ApiException("Activity was not found."); //not found
        return userRatingRepository.findUserRatingsByActivityId(activityId);
    }

    public Double getAverageRating(Integer targetUserId) {
        if (userRepository.findUserById(targetUserId) == null)
            return null; //user not found
        Double avg = userRatingRepository.getAverageRating(targetUserId);
        return (avg == null ? 0.0 : avg); //rating not found
    }
}
