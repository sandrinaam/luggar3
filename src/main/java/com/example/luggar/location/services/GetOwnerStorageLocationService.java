package com.example.luggar.location.services;

import com.example.luggar.common.Query;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.StorageLocationResponse;
import com.example.luggar.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GetOwnerStorageLocationService implements Query<Long, List<StorageLocationResponse>> {

    private final StorageLocationMapper storageLocationMapper;

    private final UserRepository userRepository;

    private final StorageLocationRepository storageLocationRepository;

    public GetOwnerStorageLocationService(StorageLocationMapper storageLocationMapper, StorageLocationRepository storageLocationRepository, UserRepository userRepository) {
        this.storageLocationMapper = storageLocationMapper;
        this.storageLocationRepository = storageLocationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<StorageLocationResponse>> execute(Long ownerId){
        userRepository.findUserOrThrow(ownerId);

        List<StorageLocationResponse> locations = storageLocationRepository.findByOwnerId(ownerId)
                .stream()
                .map(storageLocationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(locations);
    }
}
