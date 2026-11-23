package com.example.luggar.location.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.location.dto.DeleteStorageLocationCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteStorageLocationService implements Command<DeleteStorageLocationCommand, Void> {

    private final StorageLocationRepository storageLocationRepository;

    public DeleteStorageLocationService(StorageLocationRepository storageLocationRepository) {
        this.storageLocationRepository = storageLocationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> execute(DeleteStorageLocationCommand command){
        StorageLocation location = storageLocationRepository.findStorageLocationByIdOrThrow(command.id());

        if(!location.getOwner().getId().equals(command.ownerId())){
            throw new ForbiddenException("You are not the owner of this storage location.");
        }

        storageLocationRepository.delete(location);

        return ResponseEntity.noContent().build();
    }
}
