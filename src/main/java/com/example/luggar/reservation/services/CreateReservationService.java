package com.example.luggar.reservation.services;

import com.example.luggar.common.Command;
import com.example.luggar.common.exception.BadRequestException;
import com.example.luggar.common.exception.ForbiddenException;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.location.StorageLocationRepository;
import com.example.luggar.reservation.PriceCalculator;
import com.example.luggar.reservation.Reservation;
import com.example.luggar.reservation.ReservationMapper;
import com.example.luggar.reservation.ReservationRepository;
import com.example.luggar.reservation.dto.ReservationRequest;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.reservation.enums.PaymentStatus;
import com.example.luggar.reservation.enums.ReservationStatus;
import com.example.luggar.user.User;
import com.example.luggar.user.UserRepository;
import com.example.luggar.user.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateReservationService implements Command<ReservationRequest, ReservationResponse> {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final ReservationMapper reservationMapper;

    private final StorageLocationRepository storageLocationRepository;

    private final PriceCalculator priceCalculator;

    public CreateReservationService(ReservationMapper reservationMapper, ReservationRepository reservationRepository, UserRepository userRepository, StorageLocationRepository storageLocationRepository, PriceCalculator priceCalculator) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.storageLocationRepository = storageLocationRepository;
        this.priceCalculator = priceCalculator;
    }

    @Override
    @Transactional
    public ResponseEntity<ReservationResponse> execute(ReservationRequest request){
        if(!request.endTime().isAfter(request.startTime())){
            throw new BadRequestException("End time must be after start time.");
        }

        User client = userRepository.findUserOrThrow(request.clientId());

        // dali da go mahna za da moje i admin/owner da pravi rezervaciq ako klientut ima problem
        if (client.getRole() != Role.CLIENT){
            throw new ForbiddenException("You must be a client to make a reservation.");
        }
        //

        StorageLocation location = storageLocationRepository.findStorageLocationByIdOrThrow(request.storageLocationId());


        Reservation reservation = reservationMapper.toEntity(request, client, location);

        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setPaymentStatus(PaymentStatus.PENDING);
        reservation.setQrCode(UUID.randomUUID().toString());

        reservation.setPrice(priceCalculator.calculate(reservation));

        Reservation saved = reservationRepository.save(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationMapper.toResponse(saved));


    }
}
