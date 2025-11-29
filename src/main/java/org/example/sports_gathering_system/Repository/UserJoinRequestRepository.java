package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.UserJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJoinRequestRepository extends JpaRepository<UserJoinRequest, Integer> {

    UserJoinRequest findUserJoinRequestById(Integer id);
}
