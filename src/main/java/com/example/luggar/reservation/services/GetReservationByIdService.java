package com.example.luggar.reservation.services;

import com.example.luggar.common.Query;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetReservationByIdService implements Query<Long, ReservationResponse> {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    public GetReservationByIdService(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ReservationResponse> execute(Long id){

        ReservationResponse response = reservationMapper.toResponse(reservationRepository.findReservationByIdOrThrow(id));

        return ResponseEntity.ok(response);
    }
}
