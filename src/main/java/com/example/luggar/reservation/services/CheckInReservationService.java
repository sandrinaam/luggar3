package com.example.luggar.reservation.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.ConflictException;
import com.example.luggar.reservation.Reservation;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.reservation.enums.PaymentStatus;
import com.example.luggar.reservation.enums.ReservationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckInReservationService implements Command<Long, ReservationResponse> {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public CheckInReservationService(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ReservationResponse> execute(Long id){
        Reservation reservation = reservationRepository.findReservationByIdOrThrow(id);

        if(reservation.getReservationStatus() != ReservationStatus.PENDING){
            throw new ConflictException("Reservation with id " +id + " cannot be made active.");
        }

        if(reservation.getPaymentStatus() != PaymentStatus.PAID){
            throw new ConflictException("Reservation with id "+ id+ " must be paid before check-in.");
        }

        reservation.setReservationStatus(ReservationStatus.ACTIVE);

        return ResponseEntity.ok(reservationMapper.toResponse(reservation));
    }
}
