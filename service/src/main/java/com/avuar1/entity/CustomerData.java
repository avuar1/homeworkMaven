package com.avuar1.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_data", schema = "public")
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
