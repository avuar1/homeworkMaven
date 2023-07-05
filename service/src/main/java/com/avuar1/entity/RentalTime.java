package com.avuar1.entity;

import java.time.LocalDateTime;
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
public class RentalTime {

    @Id
    private Integer id;

    private Integer carId;

    private LocalDateTime startRentalTime;

    private LocalDateTime endRentalTime;

    private Integer orderId;
}
