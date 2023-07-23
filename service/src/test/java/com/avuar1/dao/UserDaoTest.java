package com.avuar1.dao;


import com.avuar1.dto.UserFilter;
import com.avuar1.entity.User;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestDataImporter;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {


    private final static SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory(); // фабрика на все тесты
    private final UserDao userDao = UserDao.getInstance();
    private Session session; // Сессия одна на все тесты

    @BeforeAll
    public static void start(){

    }
    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @BeforeEach
    public void setup(){
        session = sessionFactory.openSession(); // сессия открывается в @BeforeEach
        session.beginTransaction(); // и здесь же открываем транзакцию
        TestDataImporter.importData(session); // Генерация данных для тестов
    }

    @AfterEach
    void closeAll(){
        session.getTransaction().rollback(); // откатываем транзакцию после теста, чтобы он не сохранял данные в БД
        session.close(); //Закрываем сессию

    }

    @Test
    void findAll() {

        List<User> results = userDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");
    }

    @Test
    void findAllCriteria() {

        List<User> results = userDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

    }

    @Test
    void findAllByFirstName() {

        List<User> results = userDao.findAllByFirstName(session, "Maksim");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).fullName()).isEqualTo("Maksim Petrov");

    }

    @Test
    void findLimitedUsersOrderByEmail() {

        int limit = 3;
        List<User> results = userDao.findLimitedUsersOrderByEmail(session, limit);
        assertThat(results).hasSize(limit);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).contains("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {

        Optional<User> user = userDao.findByEmailAndPasswordWithCriteriaAPI(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {

        List<User> users = userDao.findByFirstNameAndLastNameWithCriteriaAPI(
                session, null, "Smirnov");

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Aleksey Smirnov");

    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {

        Optional<User> user = userDao.findByEmailAndPasswordWithQuerydsl(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));

    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {

        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();
        List<User> users = userDao.findByFirstNameAndLastNameWithQuerydsl(
                session, filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");

    }
}