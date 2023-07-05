package com.avuar1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    private Integer id;

    private Integer model;

    private Integer carCategoryId;

    private String colour;

    private Integer seatsQuantity;
}
