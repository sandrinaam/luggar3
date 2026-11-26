package com.example.luggar.reservation.services;

import com.example.luggar.common.Query;
import com.example.luggar.reservation.Reservation;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetReservationByQrCodeService implements Query<String, ReservationResponse> {

    private final ReservationMapper reservationMapper;

    private final ReservationRepository reservationRepository;

    public GetReservationByQrCodeService(ReservationMapper reservationMapper, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ReservationResponse> execute(String qrCode){
        Reservation reservation = reservationRepository.findReservationByQrCodeOrThrow(qrCode);

        return ResponseEntity.ok(reservationMapper.toResponse(reservation));

    }
}
