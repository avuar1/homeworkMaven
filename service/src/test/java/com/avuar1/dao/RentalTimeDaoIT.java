package com.avuar1.dao;

import com.avuar1.entity.RentalTime;
import com.avuar1.util.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestDataImporter;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalTimeDaoIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final RentalTimeDao rentalTimeDao = RentalTimeDao.getInstance();
    private Session session; // Сессия одна на все тесты

    @BeforeAll
    public static void start() {

    }

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
    void findAllRentalTimes() {

        List<RentalTime> results = rentalTimeDao.findAll(session);
        assertThat(results).hasSize(3);

        var startRentalTime1 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime2 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime3 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        List<LocalDateTime> startRentalTimes = results.stream().map(RentalTime::getStartRentalTime).collect(toList());
        assertThat(startRentalTimes).containsExactlyInAnyOrder(startRentalTime1, startRentalTime2, startRentalTime3);

        session.getTransaction().rollback();
    }


}