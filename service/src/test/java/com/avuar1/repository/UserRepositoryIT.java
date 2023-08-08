package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.dto.UserFilter;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import com.avuar1.util.TestDataImporter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class UserRepositoryIT {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveUser() {

        var user = createUser();

        userRepository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void checkDeleteUser() {
        var user = createUser();

        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        User user1 = entityManager.find(User.class, savedUser.getId());
        assertNull(user1);
    }

    @Test
    void checkUpdateUser() {

        var user = createUser();

        userRepository.save(user);
        User savedUser = entityManager.find(User.class, user.getId());
        savedUser.setFirstName("Sveta");
        userRepository.update(savedUser);

        User userUpdatedUser = entityManager.find(User.class, savedUser.getId());
        Assertions.assertThat(userUpdatedUser.getFirstName()).isEqualTo("Sveta");

    }

    @Test
    void checkFindByIdUser() {
        User user = createUser();
        userRepository.save(user);

        Optional<User> user2 = userRepository.findById(user.getId());

        assertThat(user2).isNotNull();
        user2.ifPresent(value -> assertThat(value.getFirstName()).isEqualTo("Alex"));
        user2.ifPresent(value -> assertThat(value.getEmail()).isEqualTo("alex_ivanov@gmail.com"));
    }

    @Test
    void checkfindAllUsers() {
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(3);

        List<String> fullNames = userList.stream().map(User::fullName).collect(Collectors.toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Petrov");
    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {
        Optional<User> user = userRepository.findByEmailAndPasswordWithCriteriaAPI("maks@gmail.com", "123456");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));
    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {
        Optional<User> user = userRepository.findByEmailAndPasswordWithQuerydsl("maks@gmail.com", "123456");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {
        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();

        List<User> users = userRepository.findByFirstNameAndLastNameWithCriteriaAPI(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");
    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {
        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();
        List<User> users = userRepository.findByFirstNameAndLastNameWithQuerydsl(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov");
    }

    private User createUser() {
        return User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();
    }

}