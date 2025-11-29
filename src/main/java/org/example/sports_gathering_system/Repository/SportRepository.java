package org.example.sports_gathering_system.Repository;

import org.example.sports_gathering_system.Model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Integer> {

    Sport findSportById(Integer id);
}
