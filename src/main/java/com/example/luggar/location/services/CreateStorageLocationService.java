package com.example.luggar.location.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.CreateStorageLocationCommand;
import com.example.luggar.location.dto.StorageLocationResponse;
import com.example.luggar.user.User;
import com.example.luggar.user.UserRepository;
import com.example.luggar.user.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateStorageLocationService implements Command<CreateStorageLocationCommand, StorageLocationResponse> {

    private final StorageLocationRepository storageLocationRepository;

    private final UserRepository userRepository;

    private final StorageLocationMapper storageLocationMapper;

    public CreateStorageLocationService(StorageLocationRepository storageLocationRepository, StorageLocationMapper storageLocationMapper, UserRepository userRepository){
        this.storageLocationRepository = storageLocationRepository;
        this.storageLocationMapper = storageLocationMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<StorageLocationResponse> execute(CreateStorageLocationCommand command){
        User user = userRepository.findUserOrThrow(command.ownerId());

        if(user.getRole() == Role.CLIENT){
            throw new ForbiddenException("Clients can't create locations.");
        }

        StorageLocation location = storageLocationMapper.toEntity(command.request(), user);
        StorageLocation saved = storageLocationRepository.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(storageLocationMapper.toResponse(saved));
    }
}
