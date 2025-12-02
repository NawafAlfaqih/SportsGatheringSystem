package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
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

    public List<Sport> getAllSports(Integer adminId) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found."); //admin not found

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin."); //not admin
        return sportRepository.findAll();
    }

    public void addSport(Integer adminId, Sport sport) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found."); //admin not found

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin."); //not admin

        sportRepository.save(sport);
    }

    public void updateSport(Integer adminId, Integer id, Sport sport) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found."); //admin not found

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin."); //not admin

        Sport old = sportRepository.findSportById(id);
        if (old == null)
            throw new ApiException("Sport was not found."); //not found

        old.setName(sport.getName());
        old.setEnvironment(sport.getEnvironment());

        sportRepository.save(old);
    }

    public void deleteSport(Integer adminId, Integer id) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found."); //admin not found

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin."); //not admin

        Sport sport = sportRepository.findSportById(id);
        if (sport == null)
            throw new ApiException("Sport was not found."); //not found

        sportRepository.delete(sport);
    }

    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1; //valid admin
    }
}
