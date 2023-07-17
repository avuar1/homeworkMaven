package com.avuar1.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@NamedEntityGraph(name = "WithUsersAndOrders",
        attributeNodes = {
                @NamedAttributeNode("customerData"),
                @NamedAttributeNode("orders")
        }
)

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @EqualsAndHashCode.Include
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CustomerData customerData;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public String fullName() {
        return firstName + " " + lastName;
    }
}


//TODO Сделать тест на все сущности проверить UserIT
//TODO Сделать маппинг между таблицами
