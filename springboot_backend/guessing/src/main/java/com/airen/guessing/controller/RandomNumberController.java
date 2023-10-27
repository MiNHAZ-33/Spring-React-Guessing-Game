package com.airen.guessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.airen.guessing.model.UserInfo;
import com.airen.guessing.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RandomNumberController {
    private final UserRepository userRepository;

    public RandomNumberController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateAndSaveRandomNumber(
            @RequestParam(name = "userName") String userName) {

        Map<String, String> responseMap = new HashMap<>();
        // Check if the userName is empty or null
        if (userName == null || userName.isEmpty()) {
            responseMap.put("message", "userName is required.");
            return ResponseEntity.badRequest().body(responseMap);
        }

        List<UserInfo> usersWithUsername = userRepository.findByUsername(userName);

        // Create an instance of the Random class
        Random random = new Random();
        // Generate a random number between 1 and 10 (inclusive)
        int randomNumber = random.nextInt(10) + 1;

        if (usersWithUsername.isEmpty()) {
            UserInfo user = new UserInfo();
            user.setUserName(userName);
            user.setAttempts(0);
            user.setNumber(randomNumber);
            user.setWins(0);
            user.setLosses(0);
            user.setAttempts(0);
            userRepository.save(user);
        } else {
            UserInfo user = usersWithUsername.get(0);
            user.setAttempts(0);
            user.setNumber(randomNumber);
            userRepository.save(user);
        }

        // Return the success message
        responseMap.put("message", "Number successfully generated!");
        return ResponseEntity.ok(responseMap);
    }
}
