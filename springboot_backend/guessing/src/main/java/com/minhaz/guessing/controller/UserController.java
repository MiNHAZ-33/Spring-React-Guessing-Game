package com.minhaz.guessing.controller;

import com.minhaz.guessing.dto.UserInfoDTO;
import com.minhaz.guessing.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profiles")
    public List<UserInfoDTO> getUserInfoNameWinLost() {
        List<UserInfoDTO> userInfoDTOList = userRepository.findAll()
                .stream()
                .map(userInfo -> new UserInfoDTO(userInfo.getUserName(), userInfo.getWins(), userInfo.getLosses()))
                .collect(Collectors.toList());

        return userInfoDTOList;
    }
}
