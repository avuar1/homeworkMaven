package com.avuar1.entity;

import java.util.*;
import javax.persistence.*;
import lombok.*;


@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car_category")
public class CarCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_level")
    @EqualsAndHashCode.Include
    private CategoryLevel categoryLevel;

    @EqualsAndHashCode.Include
    private Double dayPrice;

    @OneToMany(mappedBy = "carCategory", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars = new ArrayList<>();
}
