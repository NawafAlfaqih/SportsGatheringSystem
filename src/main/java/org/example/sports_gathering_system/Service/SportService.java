package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.Sport;
import org.example.sports_gathering_system.Repository.SportRepository;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;
    private final UserRepository userRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Integer addSport(Integer adminId, Sport sport) {
        Integer check = checkAdmin(adminId);
        if (check != 1)
            return check; //admin check => -1 or -2

        sportRepository.save(sport);
        return 1;
    }

    public Integer updateSport(Integer adminId, Integer id, Sport sport) {
        Integer check = checkAdmin(adminId);
        if (check != 1)
            return check; //admin check => -1 or -2

        Sport old = sportRepository.findSportById(id);
        if (old == null)
            return -3; //not found

        old.setName(sport.getName());
        old.setEnvironment(sport.getEnvironment());

        sportRepository.save(old);
        return 1;
    }

    public Integer deleteSport(Integer adminId, Integer id) {
        Integer check = checkAdmin(adminId);
        if (check != 1)
            return check; //admin check => -1 or -2

        Sport sport = sportRepository.findSportById(id);
        if (sport == null)
            return -3; //not found

        sportRepository.delete(sport);
        return 1;
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //valid admin
    }
}
