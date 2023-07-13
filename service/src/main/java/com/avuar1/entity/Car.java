package com.avuar1.entity;


import javax.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode()
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CarModel carModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CarCategory carCategory;

    private String colour;

    private Integer seatsQuantity;
}
