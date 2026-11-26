package com.example.luggar.reservation;

import com.example.luggar.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByStorageLocationOwnerId(Long ownerId);
    boolean existsByClientId(Long clientId);
    boolean existsByStorageLocationId(Long locationId);
    Optional<Reservation> findByQrCode(String qrCode);
    List<Reservation> findByStorageLocationId(Long locationId);

    default Reservation findReservationByIdOrThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation with id "+id + " not found.")
        );
    }

    default Reservation findReservationByQrCodeOrThrow(String qrCode){
        return findByQrCode(qrCode).orElseThrow(
                () -> new ResourceNotFoundException("Reservation with this QR code not found.")
        );
    }
}
