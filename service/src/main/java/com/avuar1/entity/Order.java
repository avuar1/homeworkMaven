package com.avuar1.entity;

import java.time.LocalDate;
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
public class Order {

    @Id
    private Integer id;

    private Integer userId;

    private Integer carId;

    private LocalDate rentalStart;

    private LocalDate rentalEnd;

    private String status;

    private String message;

}
