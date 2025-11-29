package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {

    UserActivity findUserActivityById(Integer id);

}
