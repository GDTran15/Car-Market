package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.dto.user.request.UserRegisterRequestDTO;
import com.duong.RestaurantManagement.exception.DuplicateResourceException;
import com.duong.RestaurantManagement.model.User;
import com.duong.RestaurantManagement.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    @Transactional
    public void registerANewUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        boolean usernameExist = userRepo.existsByUsername(userRegisterRequestDTO.username());
        boolean emailExist = userRepo.existsByEmail(userRegisterRequestDTO.email());
        boolean phoneExist = userRepo.existsByPhone(userRegisterRequestDTO.phone());
    if (usernameExist) {
        throw new DuplicateResourceException("Username already exists");
    }else if (emailExist) {
        throw new DuplicateResourceException("Email already exists");
    }else if (phoneExist) {
        throw new DuplicateResourceException("Phone already exists");
    }

    User user = User.builder()
            .username(userRegisterRequestDTO.username())
            .password(userRegisterRequestDTO.password())
            .email(userRegisterRequestDTO.email())
            .phone(userRegisterRequestDTO.phone())
            .createdAt(LocalDate.now())
            .role(userRegisterRequestDTO.role())
            .build();
    userRepo.save(user);
    }

    public boolean checkIfUsernameExist(User user){
        return userRepo.existsByUsername(user.getUsername());

    }

}

