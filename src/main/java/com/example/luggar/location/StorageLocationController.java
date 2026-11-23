package com.example.luggar.location;

import com.example.luggar.location.dto.*;
import com.example.luggar.location.services.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StorageLocationController {

    private final CreateStorageLocationService createStorageLocationService;

    private final DeleteStorageLocationService deleteStorageLocationService;

    private final GetOwnerStorageLocationService getOwnerStorageLocationService;

    private final GetStorageLocationByIdService getStorageLocationByIdService;

    private final GetStorageLocationsService getStorageLocationsService;

    private final UpdateStorageLocationService updateStorageLocationService;

    public StorageLocationController(CreateStorageLocationService createStorageLocationService,
                                     DeleteStorageLocationService deleteStorageLocationService,
                                     GetOwnerStorageLocationService getOwnerStorageLocationService,
                                     GetStorageLocationByIdService getStorageLocationByIdService,
                                     GetStorageLocationsService getStorageLocationsService,
                                     UpdateStorageLocationService updateStorageLocationService) {
        this.createStorageLocationService = createStorageLocationService;
        this.deleteStorageLocationService = deleteStorageLocationService;
        this.getOwnerStorageLocationService = getOwnerStorageLocationService;
        this.getStorageLocationByIdService = getStorageLocationByIdService;
        this.getStorageLocationsService = getStorageLocationsService;
        this.updateStorageLocationService = updateStorageLocationService;
    }

    @PostMapping("/location/owner/{ownerId}")
    public ResponseEntity<StorageLocationResponse> createStorageLocation(@PathVariable Long ownerId, @Valid @RequestBody StorageLocationRequest request){
        return createStorageLocationService.execute(new CreateStorageLocationCommand(ownerId, request));
    }

    @GetMapping("/location")
    public ResponseEntity<List<StorageLocationResponse>> getLocations(){
        return getStorageLocationsService.execute(null);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<StorageLocationResponse> getLocationById(@PathVariable Long id){
        return getStorageLocationByIdService.execute(id);
    }

    @GetMapping("/location/owner/{id}")
    public ResponseEntity<List<StorageLocationResponse>> getOwnerLocations(@PathVariable @Valid Long id){
        return getOwnerStorageLocationService.execute(id);
    }

    @PutMapping("/location/owner/{ownerId}/{id}")
    public ResponseEntity<StorageLocationResponse> updateStorageLocation(@PathVariable Long ownerId, @PathVariable Long id, @Valid @RequestBody StorageLocationRequest request){
        return updateStorageLocationService.execute(new UpdateStorageLocationCommand(ownerId, id, request));
    }

    @DeleteMapping("/location/owner/{ownerId}/{id}")
    public ResponseEntity<Void> deleteStorageLocation(@PathVariable Long ownerId, @PathVariable Long id){
        return deleteStorageLocationService.execute(new DeleteStorageLocationCommand(ownerId, id));
    }
}
