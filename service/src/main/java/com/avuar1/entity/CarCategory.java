package com.avuar1.entity;

import java.util.List;
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
@Table(name = "car_category", schema = "public")
public class CarCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_level")
    private CategoryLevel categoryLevel;

    private Double dayPrice;

    @OneToMany(mappedBy = "carCategory", cascade = CascadeType.ALL)
    private List<Car> cars;
}
