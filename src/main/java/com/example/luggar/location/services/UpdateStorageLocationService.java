package com.example.luggar.location.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.location.StorageLocationMapper;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.StorageLocationRequest;
import com.example.luggar.location.dto.StorageLocationResponse;
import com.example.luggar.location.dto.UpdateStorageLocationCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateStorageLocationService implements Command<UpdateStorageLocationCommand, StorageLocationResponse> {

    private final StorageLocationRepository storageLocationRepository;

    private final StorageLocationMapper storageLocationMapper;

    public UpdateStorageLocationService(StorageLocationMapper storageLocationMapper, StorageLocationRepository storageLocationRepository) {
        this.storageLocationMapper = storageLocationMapper;
        this.storageLocationRepository = storageLocationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<StorageLocationResponse> execute(UpdateStorageLocationCommand command){
        Long id = command.id();

        StorageLocationRequest request = command.request();

        StorageLocation storageLocation = storageLocationRepository.findStorageLocationByIdOrThrow(id);

        if(!storageLocation.getOwner().getId().equals(command.ownerId())){
            throw new ForbiddenException("You are not the owner of this storage location.");
        }

        storageLocationMapper.updateEntity(storageLocation, request);

        StorageLocation saved = storageLocationRepository.save(storageLocation);

        return ResponseEntity.ok(storageLocationMapper.toResponse(saved));
    }
}
