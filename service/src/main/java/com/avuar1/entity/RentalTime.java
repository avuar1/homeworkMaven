package com.avuar1.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rental_time", schema = "public")
public class RentalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "car_id", insertable = false, updatable = false)
    private String carId;

    private LocalDateTime startRentalTime;

    private LocalDateTime endRentalTime;

    @ManyToOne
    @JoinColumn(name ="order_id")
    private Order order;
}
