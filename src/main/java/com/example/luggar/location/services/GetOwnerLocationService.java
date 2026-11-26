package com.example.luggar.location.services;

import com.example.luggar.common.Query;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.StorageLocationResponse;
import com.example.luggar.user.User;
import com.example.luggar.user.UserRepository;
import com.example.luggar.user.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GetOwnerLocationService implements Query<Long, List<StorageLocationResponse>> {

    private final StorageLocationMapper storageLocationMapper;

    private final UserRepository userRepository;

    private final StorageLocationRepository storageLocationRepository;

    public GetOwnerLocationService(StorageLocationMapper storageLocationMapper, StorageLocationRepository storageLocationRepository, UserRepository userRepository) {
        this.storageLocationMapper = storageLocationMapper;
        this.storageLocationRepository = storageLocationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<StorageLocationResponse>> execute(Long userId){
        User user = userRepository.findUserOrThrow(userId);

        if(user.getRole() == Role.CLIENT){
            throw new ForbiddenException("Clients don't have access to this data.");
        }

        List<StorageLocationResponse> locations = storageLocationRepository.findByOwnerId(userId)
                .stream()
                .map(storageLocationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(locations);
    }
}
