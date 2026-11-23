package com.example.luggar.user.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ConflictException;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.user.User;
import com.example.luggar.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService implements Command<Long, Void> {

    private final UserRepository userRepository;

    private final StorageLocationRepository storageLocationRepository;

    public DeleteUserService(UserRepository userRepository, StorageLocationRepository storageLocationRepository) {
        this.userRepository = userRepository;
        this.storageLocationRepository = storageLocationRepository;
    }

    @Override
    public ResponseEntity<Void> execute (Long id){
        User user = userRepository.findUserOrThrow(id);

        if(storageLocationRepository.existsByOwnerId(id)){
            throw new ConflictException("User has storage locations and cannot be deleted.");
        }

        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }
}
