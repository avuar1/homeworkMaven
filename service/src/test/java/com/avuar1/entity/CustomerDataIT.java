package com.avuar1.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.*;


class CustomerDataIT {

//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @Column(unique = true)
//    private String driverLicenseNumber;
//
//    private LocalDate driverLicenseExpiration;
//
//    private Double creditAmount;
    private SessionFactory sessionFactory;
    private Session session;
    private User user;
    private CustomerData customerData;

    @BeforeAll
    void  setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();

        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("ivam@Gmail.com")
                .password("test")
                .role(Role.CLIENT)
                .build();

    CustomerData customerData = CustomerData.builder()
            .user(user)
            .driverLicenseNumber("343423423")
            .driverLicenseExpiration(LocalDate.of(2024, 01, 23))
            .creditAmount(1450.00)
            .build();
    }

    @Test
    void createCarCategoryTest() {
        //*******************************
        // Денис как делать тест если по факту customerData зависит от User, а User от Сustomer Data
        // Это получается я что то с мэппингом наворотил?
        //*****************************
        session.beginTransaction();
        session.save(customerData);
        session.save(user);

        CustomerData actualRezult= session.get(CustomerData.class, customerData.getId());

        assertNotNull(actualRezult.getId());
        assertNotNull(user.getId());
        assertEquals(customerData.getId(), user.getCustomerData().getId());
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(user);
        session.delete(customerData);
        session.getTransaction().commit();

    }

    @Test
    void getCarCategoryTest() {


    }

    @Test
    void updateCarCategoryTest() {
    }

    @Test
    void deleteCarCategoryTest() {

    }

    @AfterAll
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @AfterEach
    public void closeSession() {

        if (session != null) {
            session.close();
        }
    }

}