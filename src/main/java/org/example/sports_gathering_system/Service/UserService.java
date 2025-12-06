package org.example.sports_gathering_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.sports_gathering_system.Api.ApiException;
import org.example.sports_gathering_system.Model.User;
import org.example.sports_gathering_system.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AiService aiService;

    public List<User> getAllUsers(Integer adminId) {
        if (checkAdmin(adminId) == -1)
            throw new ApiException("Admin was not found."); //not found

        if (checkAdmin(adminId) == -2)
            throw new ApiException("This user is not an admin"); //not admin

        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setCurrentActivityId(null);

        userRepository.save(user);
    }

    public void updateUser(Integer userId, Integer id, User user) {
        if (!userId.equals(id) && checkAdmin(userId) != 1)
            throw new ApiException("Not allowed to update this account"); //not the same user or admin

        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            throw new ApiException("User was not found"); //not found

        oldUser.setFavoriteSportId(user.getFavoriteSportId());
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

        oldUser.setCurrentActivityId(oldUser.getCurrentActivityId());

        userRepository.save(oldUser);
    }

    public void deleteUser(Integer userId, Integer id) {
        if (!userId.equals(id) && checkAdmin(userId) != 1)
            throw new ApiException("Not allowed to delete this account"); //not the same user or admin

        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("User was not found"); //not found

        userRepository.delete(user);
    }

    public String bodyInformation(User user) {
        return "Height: " + user.getHeight()
                + " Weight: " + user.getWeight()
                + " Age: " + user.getAge()
                + " Gender: " + user.getGender()
                + " Bio: " + user.getBio();
    }

    public String askAi(Integer userID, String moodPrompt) {
        User user = userRepository.findUserById(userID);
        if (user == null)
            throw new ApiException("User was not found");

        String prompt = """
                recommend to the user a sport to play
                Based on the Body Information %s given by the user %s
                and the user's mood %s.
                """.formatted(bodyInformation(user), user.getUsername(), moodPrompt);

        return aiService.chat(prompt);
    }


    public Integer checkAdmin(Integer userId) {
        if (userRepository.findUserById(userId) == null)
            return -1; //not found

        if (userRepository.findAdminById(userId) == null)
            return -2; //not admin

        return 1;
    }
}
