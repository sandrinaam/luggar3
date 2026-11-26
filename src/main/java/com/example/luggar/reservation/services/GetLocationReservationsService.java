package com.example.luggar.reservation.services;

import com.example.luggar.common.Query;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetLocationReservationsService implements Query<Long, List<ReservationResponse>> {

    private final ReservationMapper reservationMapper;

    private final StorageLocationRepository storageLocationRepository;

    private final ReservationRepository reservationRepository;

    public GetLocationReservationsService(ReservationMapper reservationMapper, StorageLocationRepository storageLocationRepository, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.storageLocationRepository = storageLocationRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationResponse>> execute(Long locationId){
        storageLocationRepository.findStorageLocationByIdOrThrow(locationId);

        List<ReservationResponse> reservations = reservationRepository.findByStorageLocationId(locationId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }
}
