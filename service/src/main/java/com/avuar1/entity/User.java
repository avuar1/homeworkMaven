package com.avuar1.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NamedEntityGraph(name = "WithUsersAndOrders",
        attributeNodes = {
                @NamedAttributeNode(value = "customerData", subgraph = "customerData"),
                @NamedAttributeNode("orders")
        },
        subgraphs = {
        @NamedSubgraph(name = "customerData", attributeNodes = @NamedAttributeNode("driverLicenseNumber"))
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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
