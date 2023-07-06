//package com.avuar1.entity;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.*;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//import util.HibernateTestUtil;
//
//
//
//@Slf4j
//class RentalTimeIT {
//
//    private static SessionFactory sessionFactory;
//    private Session session;
//
//    @BeforeAll
//    void setup() {
//        sessionFactory = HibernateTestUtil.buildSessionFactory();
//        log.info("SessionFactory created");
//    }
//
//    @AfterAll
//    void tearDown() {
//        if (sessionFactory != null) {
//            sessionFactory.close();
//        }
//        log.info("SessionFactory destroyed");
//    }
//
//    @Test
//    void createRentalTimeTest() {
////        @Id
////        @GeneratedValue(strategy = GenerationType.IDENTITY)
////        private Integer id;
////
////        private String category;
////
////        private Double dayPrice;
////
////        @OneToMany(mappedBy = "carCategory", cascade = CascadeType.ALL)
////        private List<Car> cars;
//
//        CarCategory carCategory = CarCategory.builder()
//                .categoryCar(CategoryCar.PREMIUM)
//                .dayPrice(1500.00)
//                .cars(new ArrayList<>())
//                .build();
//
//        Car car = Car.builder()
//                .carModel(CarModel.MERSEDES)
//                .carCategory(carCategory)
//                .colour("Red")
//                .seatsQuantity(4)
//                .build();
//
//        RentalTime rentaltime = RentalTime.builder()
//                .car(car)
//                .startRentalTime(LocalDateTime.parse("2023-10-07 12:00:00"))  //2021-09-30T15:30:00
//                .endRentalTime(LocalDateTime.parse("2023-11-07 12:00:00"))
//                .order(null)
//                .build();
//
//    }
////    @Id  // Написать Скрипит
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Integer id;
////
////    @Enumerated(EnumType.STRING)
////    private CarModel carModel;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "category_id", referencedColumnName = "id")
////    private CarCategory carCategory;
////
////    private String colour;
////
////    private Integer seatsQuantity;
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Integer id;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "car_id", referencedColumnName = "id")
////    private Car car;
////
////    @Column(name = "car_id", insertable = false, updatable = false)
////    private String carId;
////
////    private LocalDateTime startRentalTime;
////
////    private LocalDateTime endRentalTime;
////
////    @ManyToOne
////    @JoinColumn(name ="order_id")
////    private Order order;
//
//
//    @Test
//    void updateUserTest() {
//
//    }
//
//    @Test
//    void getUserTest() {
//
//
//    }
//
//    @Test
//    void deleteUserTest() {
//
//    }
//
//    @BeforeEach
//    public void  openSession(){
//        session = sessionFactory.openSession();
//        log.info("Session created");
//    }
//
//    @AfterEach
//    public void closeSession(){
//        if (session != null) {
//            session.close();
//        }
//        log.info("Session closed");
//    }
//
//}