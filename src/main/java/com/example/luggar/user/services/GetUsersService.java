package com.example.luggar.user.services;

import com.example.luggar.user.User;
import com.example.luggar.user.UserMapper;
import com.example.luggar.user.UserRepository;
import com.example.luggar.common.Query;
import com.example.luggar.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetUsersService implements Query<Void, List<UserResponse>> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetUsersService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserResponse>> execute(Void input) {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = users
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
