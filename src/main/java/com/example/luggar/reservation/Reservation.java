package com.example.luggar.reservation;

import com.example.luggar.item.Item;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.reservation.enums.PaymentStatus;
import com.example.luggar.reservation.enums.ReservationStatus;
import com.example.luggar.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus reservationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "qr_code", nullable = false, unique = true, length = 64)
    private String qrCode;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private StorageLocation storageLocation;


    public void addItem(Item item) {
        item.setReservation(this);
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setReservation(null);
    }
}
