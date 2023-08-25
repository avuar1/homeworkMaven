package com.avuar1.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode
@ToString(exclude = {"rentalTimes", "car", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private Car car;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private OrderStatus orderStatus;


    private String message;

    @Builder.Default
    @OneToMany(mappedBy = "order")
    @EqualsAndHashCode.Exclude
    private List<RentalTime> rentalTimes = new ArrayList<>();

}
