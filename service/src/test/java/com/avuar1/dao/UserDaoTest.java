package com.avuar1.dao;

import com.avuar1.dto.*;
import com.avuar1.entity.*;
import com.avuar1.util.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.hibernate.*;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import util.*;


import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final UserDao userDao = UserDao.getInstance();

    @BeforeAll
    public void initDb(){
        // Для генерации данных в таблице
//        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

        session.getTransaction().commit();
    }

    @Test
    void findAllCriteria() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

        session.getTransaction().commit();
    }

    @Test
    void findAllByFirstName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userDao.findAllByFirstName(session, "Maksim");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).fullName()).isEqualTo("Maksim Petrov");

        session.getTransaction().commit();
    }

    @Test
    void findLimitedUsersOrderByEmail() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 3;
        List<User> results = userDao.findLimitedUsersOrderByEmail(session, limit);
        assertThat(results).hasSize(limit);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).contains("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

        session.getTransaction().commit();
    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<User> user = userDao.findByEmailAndPasswordWithCriteriaAPI(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));

        session.getTransaction().commit();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> users = userDao.findByFirstNameAndLastNameWithCriteriaAPI(
                session, null, "Smirnov");

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Aleksey Smirnov");

        session.getTransaction().commit();
    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<User> user = userDao.findByEmailAndPasswordWithQuerydsl(
                session, "test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));

        session.getTransaction().commit();
    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();
        List<User> users = userDao.findByFirstNameAndLastNameWithQuerydsl(
                session, filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");

        session.getTransaction().commit();
    }

}