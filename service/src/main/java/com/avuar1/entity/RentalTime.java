package com.avuar1.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rental_time", schema = "public")
public class RentalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @ToString.Exclude
    private Car car;

    @EqualsAndHashCode.Include
    private LocalDateTime startRentalTime;

    @EqualsAndHashCode.Include
    private LocalDateTime endRentalTime;

    @ManyToOne
    @JoinColumn(name ="order_id")
    @ToString.Exclude
    private Order order;
}
