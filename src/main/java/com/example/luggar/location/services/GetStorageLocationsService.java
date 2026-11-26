package com.example.luggar.location.services;

import com.example.luggar.common.Query;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.StorageLocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetStorageLocationsService implements Query<Void, List<StorageLocationResponse>> {

    private final StorageLocationRepository storageLocationRepository;
    private final StorageLocationMapper storageLocationMapper;

    public GetStorageLocationsService(StorageLocationMapper storageLocationMapper, StorageLocationRepository storageLocationRepository) {
        this.storageLocationMapper = storageLocationMapper;
        this.storageLocationRepository = storageLocationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<StorageLocationResponse>> execute(Void input){
        List<StorageLocation> storageLocations = storageLocationRepository.findAll();

        List<StorageLocationResponse> response = storageLocations
                .stream()
                .map(storageLocationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
