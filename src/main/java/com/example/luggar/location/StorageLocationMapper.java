package com.example.luggar.location;

import com.example.luggar.user.User;
import com.example.luggar.location.dto.StorageLocationRequest;
import com.example.luggar.location.dto.StorageLocationResponse;
import org.springframework.stereotype.Component;

@Component
public class StorageLocationMapper {

    public StorageLocation toEntity(StorageLocationRequest request, User owner){
        StorageLocation location = new StorageLocation();
        location.setName(request.name());
        location.setAddress(request.address());
        location.setCapacity(request.capacity());
        location.setOwner(owner);

        return location;
    }

    public StorageLocationResponse toResponse(StorageLocation location){
        return new StorageLocationResponse(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getOwner().getId()
        );
    }

    public void updateEntity(StorageLocation location, StorageLocationRequest request){
        location.setName(request.name());
        location.setAddress(request.address());
        location.setCapacity(request.capacity());
    }
}
