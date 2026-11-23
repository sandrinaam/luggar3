package com.example.luggar.location;

import com.example.luggar.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocation, Long> {

    List<StorageLocation> findByOwnerId(Long ownerId);
    boolean existsByOwnerId(Long ownerId);
    default StorageLocation findStorageLocationByIdOrThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Storage location with id " +id + " not found."));
    }
}
