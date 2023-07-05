package com.avuar1.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CustomerData {

    @Id
    private Integer id;

    private Integer userId;

    private String driverLicenseNumber;

    private LocalDate driverLicenseExpiration;

    private Double creditAmount;
}
