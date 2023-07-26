package com.avuar1.repository;

import com.avuar1.entity.RentalTime;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestDataImporter;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalTimeRepositoryIT {

    private final static SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory(); // фабрика на все тесты
    private Session session; // Сессия на каждый тест своя


    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @BeforeEach
    public void setup() {
        session = sessionFactory.openSession(); // сессия открывается в @BeforeEach
        session.beginTransaction(); // и здесь же открываем транзакцию
        TestDataImporter.importData(session); // Генерация данных для тестов
    }

    @AfterEach
    void closeAll() {
        session.getTransaction().rollback(); // откатываем транзакцию после теста, чтобы он не сохранял данные в БД
        session.close(); //Закрываем сессию

    }

    @Test
    void checkSaveRentalTime() {

        RentalTime rentalTime = RentalTime.builder()
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 00, 00))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 00, 00))
                .build();

        var rentalTimeRepository = new RentalTimeRepository(session);
        var save = rentalTimeRepository.save(rentalTime);

        assertNotNull(save.getId());
    }

    @Test
    void checkDeleteRentalTime() {

        var rentalTimeRepository = new RentalTimeRepository(session);
        RentalTime rentalTime = RentalTime.builder()
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 00, 00))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 00, 00))
                .build();

        session.save(rentalTime);
        session.flush();
        rentalTimeRepository.delete(rentalTime);

        RentalTime rentalTime2 = session.get(RentalTime.class, rentalTime.getId());
        assertNull(rentalTime2);

    }

    @Test
    void checkUpdateRentalTime() {
        var rentalTimeRepository = new RentalTimeRepository(session);
        RentalTime rentalTime = RentalTime.builder()
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 00, 00))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 00, 00))
                .build();
        session.save(rentalTime);
        session.flush();

        RentalTime rentalTime2 = session.get(RentalTime.class, rentalTime.getId());
        rentalTime2.setStartRentalTime(LocalDateTime.of(2025, 1, 25, 12, 00,00));
        rentalTimeRepository.update(rentalTime);

        session.flush();
        RentalTime rentalTime3 = session.get(RentalTime.class, rentalTime2.getId());
        assertThat(rentalTime3.getStartRentalTime())
                .isEqualTo(LocalDateTime.of(2025, 1, 25, 12, 00, 00));

    }

//    @Test
//    void checkFindByIdRentalTime() {
//
//        var rentalTimeRepository = new RentalTimeRepository(session);
//
//        RentalTime rentalTime = RentalTime.builder()
//                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 00, 00))
//                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 00, 00))
//                .build();
//        session.save(rentalTime);
//        session.flush();
//
//        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 00,00);
//        var endTime = LocalDateTime.of(2020, 1, 29, 18, 00,00);
//
//        Optional<RentalTime> rentalTime = rentalTimeRepository.findById(rentalTime.get().getId());
//        rentalTime.ifPresent(System.out::println);
//
//        assertThat(rentalTime).isNotNull();
//        rentalTime.ifPresent(value -> assertThat(value.getBeginTime()).isEqualTo(beginTime));
//        rentalTime.ifPresent(value -> assertThat(value.getEndTime()).isEqualTo(endTime));
//
//        session.getTransaction().rollback();
//    }

    @Test
    void checkFindAllRentalTimes() {

        var rentalTimeRepository = new RentalTimeRepository(session);

        List<RentalTime> results = rentalTimeRepository.findAll();
        assertThat(results).hasSize(3);

        var startRentalTime1 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime2 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime3 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        List<LocalDateTime> beginTimes = results.stream().map(RentalTime::getStartRentalTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(startRentalTime1, startRentalTime2, startRentalTime3);

    }

}