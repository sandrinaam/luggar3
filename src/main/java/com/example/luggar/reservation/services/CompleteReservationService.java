package com.example.luggar.reservation.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ConflictException;
import com.example.luggar.reservation.Reservation;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.reservation.enums.ReservationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteReservationService implements Command<Long, ReservationResponse> {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public CompleteReservationService(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ReservationResponse> execute(Long id){
        Reservation reservation = reservationRepository.findReservationByIdOrThrow(id);

        if(reservation.getReservationStatus() != ReservationStatus.ACTIVE){
            throw new ConflictException("Reservation with id " +id + " cannot be completed.");
        }

        reservation.setReservationStatus(ReservationStatus.COMPLETED);

        return ResponseEntity.ok(reservationMapper.toResponse(reservation));
    }
}
