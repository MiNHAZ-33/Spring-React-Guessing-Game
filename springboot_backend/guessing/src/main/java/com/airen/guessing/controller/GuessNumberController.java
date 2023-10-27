package com.airen.guessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.airen.guessing.model.UserInfo;
import com.airen.guessing.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class GuessNumberController {

    private final UserRepository userRepository;

    public GuessNumberController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/guess")
    public ResponseEntity<?> checkUserGuess(
            @RequestParam("userName") String userName,
            @RequestParam("guess") Integer userGuess) {
        // Check if the username is empty or null
        Map<String, String> responseMap = new LinkedHashMap<>();
        if (userName == null || userName.isEmpty()) {
            responseMap.put("message", "userName is required.");
            return ResponseEntity.badRequest().body(responseMap);
        }
        if (userGuess == null) {
            responseMap.put("message", "Please provide a valid integer between 1 to 10 as your guess");
            return ResponseEntity.badRequest().body(responseMap);
        }

        List<UserInfo> usersWithUsername = userRepository.findByUsername(userName);
        if (usersWithUsername.isEmpty()) {
            responseMap.put("message", "No user with the given username found.");
            return ResponseEntity.badRequest().body(responseMap);
        }
        UserInfo user = usersWithUsername.get(0);
        int attempts = user.getAttempts();
        // Increment the attempts count
        user.setAttempts(attempts + 1);
        userRepository.save(user);

        int randomNumber = user.getNumber();

        if (userGuess == randomNumber) {
            responseMap.put("status", "success");
            responseMap.put("message", "Success! You guessed the correct number: " + randomNumber +
                    ". Total attempts: " + user.getAttempts());
            responseMap.put("extra", "Total attempts taken: " + user.getAttempts());
            int totalWins = user.getWins() + 1;
            user.setWins(totalWins);
            userRepository.save(user);
        } else if (userGuess < randomNumber) {
            responseMap.put("status", "low");
            responseMap.put("message", "You guessed too low. The correct number is higher. " +
                    "Total attempts: " + user.getAttempts());
            if (user.getAttempts() >= 3) {
                responseMap.put("extra", "You exceeded the max attempt.");
                user.setAttempts(0);
                int totalLosses = user.getLosses() + 1;
                user.setLosses(totalLosses);
                userRepository.save(user);
            }
        } else {
            responseMap.put("status", "high");
            responseMap.put("message", "You guessed too high. The correct number is lower. " +
                    "Total attempts: " + user.getAttempts());
            if (user.getAttempts() >= 3) {
                responseMap.put("extra", "You exceeded the max attempt.");
                user.setAttempts(0);
                int totalLosses = user.getLosses() + 1;
                user.setLosses(totalLosses);
                userRepository.save(user);
            }
        }
        return ResponseEntity.ok(responseMap);
    }

}