package com.example.luggar.user.services;

import com.example.luggar.common.Command;
import com.example.luggar.user.User;
import com.example.luggar.user.UserMapper;
import com.example.luggar.user.UserRepository;
import com.example.luggar.common.exception.DuplicateResourceException;
import com.example.luggar.user.dto.UpdateUserCommand;
import com.example.luggar.user.dto.UserRequest;
import com.example.luggar.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService implements Command<UpdateUserCommand, UserResponse> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UpdateUserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> execute(UpdateUserCommand command) {
        Long id = command.id();
        UserRequest request = command.request();

        User user = userRepository.findUserOrThrow(id);

        if (!request.email().equals(user.getEmail()) && userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email " + request.email() + " is already used.");
        }

        userMapper.updateEntity(user, request);

        User saved = userRepository.save(user);

        return ResponseEntity.ok(userMapper.toResponse(saved));
    }
}
