package com.example.luggar.location.dto;

public record UpdateStorageLocationCommand(Long ownerId, Long id, StorageLocationRequest request) {}
