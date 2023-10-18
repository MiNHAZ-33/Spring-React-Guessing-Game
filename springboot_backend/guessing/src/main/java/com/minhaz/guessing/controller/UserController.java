package com.minhaz.guessing.controller;

import com.minhaz.guessing.model.UserInfo;
import com.minhaz.guessing.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestParam("userName") String userName) {
        List<UserInfo> usersWithUsername = userRepository.findByUsername(userName);
        Map<String, Object> responseMap = new LinkedHashMap<>();
        if (usersWithUsername.isEmpty()) {
            responseMap.put("message", "No user with the given username found.");
            return ResponseEntity.badRequest().body(responseMap);
        }
        UserInfo user = usersWithUsername.get(0);
        responseMap.put("userName", user.getUserName());
        responseMap.put("gameWon", user.getWins());
        responseMap.put("gameLost", user.getLosses());

        return ResponseEntity.ok(responseMap);
    }
}
