package com.avuar1.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_data")
public class CustomerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @ToString.Include
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String driverLicenseNumber;

    @EqualsAndHashCode.Include
    @ToString.Include
    private LocalDate driverLicenseExpiration;

    @EqualsAndHashCode.Include
    private Double creditAmount;

    public void setUser(User user){
        user.setCustomerData(this);
        this.user = user;
    }
}
