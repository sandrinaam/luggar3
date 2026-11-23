package com.example.luggar.item;

import com.example.luggar.item.enums.ItemType;
import com.example.luggar.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "reservation_id", nullable = false)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

}
