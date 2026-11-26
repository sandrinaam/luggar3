package com.example.luggar.reservation.services;

import com.example.luggar.common.Query;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetClientReservationsService implements Query<Long, List<ReservationResponse>> {

    private final ReservationMapper reservationMapper;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    public GetClientReservationsService(ReservationMapper reservationMapper, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationResponse>> execute(Long clientId){
        userRepository.findUserOrThrow(clientId);

        List<ReservationResponse> reservations = reservationRepository.findByClientId(clientId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }
}
