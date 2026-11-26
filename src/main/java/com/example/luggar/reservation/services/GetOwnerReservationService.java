package com.example.luggar.reservation.services;

import com.example.luggar.common.Query;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.user.User;
import com.example.luggar.user.UserRepository;
import com.example.luggar.user.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetOwnerReservationService implements Query<Long, List<ReservationResponse>> {

    private final ReservationMapper reservationMapper;

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    public GetOwnerReservationService(ReservationMapper reservationMapper, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.reservationMapper = reservationMapper;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ReservationResponse>> execute(Long ownerId){
        User user = userRepository.findUserOrThrow(ownerId);
        if (user.getRole() == Role.CLIENT) {
            throw new ForbiddenException("Clients don't have access to this data.");
        }

        List<ReservationResponse> reservations = reservationRepository.findByStorageLocationOwnerId(ownerId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }
}
