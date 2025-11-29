package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setCurrentActivityId(null);
        user.setSportIds("[]");

        userRepository.save(user);
    }

    public Integer updateUser(Integer userId, Integer id, User user) {
        if (!userId.equals(id) && checkAdmin(userId) != 1)
            return -1; //not the same user or admin

        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            return -2; //not found

        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setGender(user.getGender());
        oldUser.setAge(user.getAge());
        oldUser.setHeight(user.getHeight());
        oldUser.setWeight(user.getWeight());
        oldUser.setBio(user.getBio());
        oldUser.setCity(user.getCity());

        oldUser.setSportIds(oldUser.getSportIds());
        oldUser.setCurrentActivityId(oldUser.getCurrentActivityId());

        userRepository.save(oldUser);
        return 1;
    }

    public Integer deleteUser(Integer userId, Integer id) {
        if (!userId.equals(id) && checkAdmin(userId) != 1)
            return -1; //not the same user or admin

        User user = userRepository.findUserById(id);
        if (user == null)
            return -2; //not found

        userRepository.delete(user);
        return 1;
    }


    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1;
    }
}
