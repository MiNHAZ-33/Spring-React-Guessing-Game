package com.airen.guessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airen.guessing.dto.UserInfoDTO;
import com.airen.guessing.model.UserInfo;
import com.airen.guessing.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
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
        responseMap.put("Total Wins", user.getWins());
        responseMap.put("Total Losses", user.getLosses());

        return ResponseEntity.ok(responseMap);
    }
    
    @GetMapping("/profiles")
    public List<UserInfoDTO> getUserInfoNameWinLost() {
        // Retrieve the user info data from your service or repository
        // Convert the UserInfo to UserInfoDTO
        List<UserInfoDTO> userInfoDTOList = userRepository.findAll()
                .stream()
                .map(userInfo -> new UserInfoDTO(userInfo.getUserName(), userInfo.getWins(), userInfo.getLosses()))
                .collect(Collectors.toList());

        return userInfoDTOList;
    }
}
