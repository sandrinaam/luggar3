package com.example.luggar.user.services;

import com.example.luggar.user.UserMapper;
import com.example.luggar.user.UserRepository;
import com.example.luggar.common.Query;
import com.example.luggar.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserByIdService implements Query<Long, UserResponse> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;



    public GetUserByIdService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponse> execute (Long id){

        UserResponse response = userMapper.toResponse(userRepository.findUserOrThrow(id));

        return ResponseEntity.ok(response);
    }

}
