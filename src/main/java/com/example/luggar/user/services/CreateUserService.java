package com.example.luggar.user.services;


import com.example.luggar.user.User;
import com.example.luggar.user.UserMapper;
import com.example.luggar.user.UserRepository;
import com.example.luggar.common.Command;
import com.example.luggar.common.exception.DuplicateResourceException;
import com.example.luggar.user.dto.UserRequest;
import com.example.luggar.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserService implements Command<UserRequest, UserResponse> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponse> execute(UserRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("Email " + request.email() + " is already used.");
        }


        User saved = userRepository.save(userMapper.toEntity(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(saved));
    }
}
