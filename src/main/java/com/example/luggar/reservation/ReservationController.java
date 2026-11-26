package com.example.luggar.reservation;

import com.example.luggar.reservation.dto.ReservationRequest;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.reservation.services.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final CreateReservationService createReservationService;

    private final CancelReservationService cancelReservationService;

    private final GetClientReservationsService getClientReservationsService;

    private final GetLocationReservationsService getLocationReservationsService;

    private final GetOwnerReservationService getOwnerReservationService;

    private final GetReservationByIdService getReservationByIdService;

    private final GetReservationByQrCodeService getReservationByQrCodeService;

    private final PayReservationService payReservationService;

    private final CheckInReservationService checkInReservationService;

    private final CompleteReservationService completeReservationService;


    public ReservationController(CancelReservationService cancelReservationService,
                                 CreateReservationService createReservationService,
                                 GetClientReservationsService getClientReservationsService,
                                 GetLocationReservationsService getLocationReservationsService,
                                 GetOwnerReservationService getOwnerReservationService,
                                 GetReservationByIdService getReservationByIdService,
                                 GetReservationByQrCodeService getReservationByQrCodeService,
                                 PayReservationService payReservationService,
                                 CheckInReservationService checkInReservationService,
                                 CompleteReservationService completeReservationService) {
        this.cancelReservationService = cancelReservationService;
        this.createReservationService = createReservationService;
        this.getClientReservationsService = getClientReservationsService;
        this.getLocationReservationsService = getLocationReservationsService;
        this.getOwnerReservationService = getOwnerReservationService;
        this.getReservationByIdService = getReservationByIdService;
        this.getReservationByQrCodeService = getReservationByQrCodeService;
        this.payReservationService = payReservationService;
        this.checkInReservationService = checkInReservationService;
        this.completeReservationService = completeReservationService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request){
        return createReservationService.execute(request);
    }

    @PatchMapping("/reservation/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id){
        return cancelReservationService.execute(id);
    }

    @GetMapping("/reservation/client/{clientId}")
    public ResponseEntity<List<ReservationResponse>> getClientReservations(@PathVariable Long clientId){
        return getClientReservationsService.execute(clientId);
    }

    @GetMapping("/reservation/location/{locationId}")
    public ResponseEntity<List<ReservationResponse>> getLocationReservations(@PathVariable Long locationId){
        return getLocationReservationsService.execute(locationId);
    }

    @GetMapping("/reservation/owner/{ownerId}")
    public ResponseEntity<List<ReservationResponse>> getOwnerReservations(@PathVariable Long ownerId){
        return getOwnerReservationService.execute(ownerId);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id){
        return getReservationByIdService.execute(id);
    }

    @GetMapping("/reservation/qr/{qrCode}")
    public ResponseEntity<ReservationResponse> getReservationByQrCode(@PathVariable String qrCode){
        return getReservationByQrCodeService.execute(qrCode);
    }

    @PatchMapping("/reservation/{id}/pay")
    public ResponseEntity<ReservationResponse> payReservation(@PathVariable Long id){
        return payReservationService.execute(id);
    }

    @PatchMapping("/reservation/{id}/check-in")
    public ResponseEntity<ReservationResponse> checkInReservation(@PathVariable Long id){
        return checkInReservationService.execute(id);
    }

    @PatchMapping("/reservation/{id}/complete")
    public ResponseEntity<ReservationResponse> completeReservation(@PathVariable Long id){
        return completeReservationService.execute(id);
    }

}
