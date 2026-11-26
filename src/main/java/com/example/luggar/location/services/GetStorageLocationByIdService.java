package com.example.luggar.location.services;

import com.example.luggar.common.Query;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.StorageLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetStorageLocationByIdService implements Query<Long, StorageLocationResponse> {

    private final StorageLocationMapper storageLocationMapper;
    private final StorageLocationRepository storageLocationRepository;

    public GetStorageLocationByIdService(StorageLocationMapper storageLocationMapper, StorageLocationRepository storageLocationRepository) {
        this.storageLocationMapper = storageLocationMapper;
        this.storageLocationRepository = storageLocationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StorageLocationResponse> execute(Long id){

        StorageLocationResponse response = storageLocationMapper.toResponse(storageLocationRepository.findStorageLocationByIdOrThrow(id));

        return ResponseEntity.ok(response);
    }
}
