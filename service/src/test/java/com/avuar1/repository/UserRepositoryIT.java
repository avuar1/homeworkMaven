package com.avuar1.repository;

import com.avuar1.dao.UserDao;
import com.avuar1.dto.UserFilter;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import java.lang.reflect.Proxy;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class UserRepositoryIT {

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
    void checkSaveUser() {

        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        var userRepository = new UserRepository(session);
        userRepository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void checkDeleteUser() {

        var userExpected = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        var userRepository = new UserRepository(session);
        userRepository.save(userExpected);
        session.flush();
        userRepository.delete(userExpected);

        User user = session.get(User.class, userExpected.getId());
        assertNull(user);

    }

    @Test
    void checkUpdateUser() {

        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        var userRepository = new UserRepository(session);
        session.save(user);
        session.flush();

        var user2 = session.get(User.class, user.getId());
        user2.setFirstName("Sveta");
        userRepository.update(user);

        session.flush();
        User user3 = session.get(User.class, user2.getId());
        assertThat(user3.getFirstName()).isEqualTo("Sveta");

    }

    @Test
    void checkFindByIdUser() {

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findById(1);

        assertThat(user).isNotNull();
        user.ifPresent(value -> assertThat(value.getFirstName()).isEqualTo("Bob"));
        user.ifPresent(value -> assertThat(value.getEmail()).isEqualTo("test1@tut.by"));

    }

    @Test
    void checkFindAllUsers() {

        var userRepository = new UserRepository(session);

        List<User> results = userRepository.findAll();
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");

    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findByEmailAndPasswordWithCriteriaAPI(
                session, "test1@gmail.com", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));

    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {

        var userRepository = new UserRepository(session);

        Optional<User> user = userRepository.findByEmailAndPasswordWithQuerydsl(
                session, "test1@gmail.com", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));

    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {

        var userRepository = new UserRepository(session);

        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();

        List<User> users = userRepository.findByFirstNameAndLastNameWithCriteriaAPI(
                session, filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");

    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {

        var userRepository = new UserRepository(session);

        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();
        List<User> users = userRepository.findByFirstNameAndLastNameWithQuerydsl(
                session, filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");

    }


}