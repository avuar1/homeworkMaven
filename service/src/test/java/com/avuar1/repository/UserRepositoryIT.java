package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.dao.QPredicate;
import com.avuar1.dto.UserFilter;
import static com.avuar1.entity.QUser.user;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql({
        "classpath:sql/data.sql"
})
@RequiredArgsConstructor
class UserRepositoryIT {

    private final UserRepository userRepository;

    @Test
    void checkSaveUser() {
        var user = createUser();

        userRepository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void checkUpdateUser() {
        var user = createUser();
        userRepository.saveAndFlush(user);

        user.setFirstName("Sveta");
        userRepository.saveAndFlush(user);

        Optional<User> userUpdated = userRepository.findById(user.getId());
        userUpdated.ifPresent(value -> assertThat(value.getFirstName()).isEqualTo("Sveta"));
    }

    @Test
    void checkDeleteUser() {
        var user = createUser();

        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        Optional<User> user1 = userRepository.findById(savedUser.getId());

        assertTrue(user1.isEmpty());
    }

    @Test
    void checkFindByIdUser() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(3);

        List<String> fullNames = users.stream().map(User::fullName).collect(Collectors.toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Maksim Petrov", "Aleksey Smirnov", "Sergey Ivanov");
    }

    @Test
    void findUserByEmailAndPassword() {
        Optional<User> user = userRepository.findByEmailAndPassword("maks@gmail.com", "123456");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Maksim Petrov"));
    }

    //
    @Test
    void findUsersByFirstNameAndLastName() {
        UserFilter filter = UserFilter.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .buildAnd();

        List<User> users = (List<User>) userRepository.findAll(predicate);

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