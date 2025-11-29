package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    @Query("select u from User u where u.id = ?1 and u.role = 'Admin'")
    User findAdminById(Integer id);



}
