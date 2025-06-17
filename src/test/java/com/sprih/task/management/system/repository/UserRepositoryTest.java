package com.sprih.task.management.system.repository;

import com.sprih.task.management.system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .username("johndoe")
                .organizationId(1L)
                .userId(1001L)
                .roleId(2L)
                .roleName("Admin")
                .fullName("John Doe")
                .firstName("John")
                .lastName("Doe")
                .screenName("johnny")
                .middleName("M")
                .email("john.doe@example.com")
                .build();

        user = userRepository.save(user);
    }

    @Test
    void shouldSaveAndFindUserById() {
        Optional<User> found = userRepository.findById(user.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("johndoe");
        assertThat(found.get().getOrganizationId()).isEqualTo(1L);
        assertThat(found.get().getUserId()).isEqualTo(1001L);
        assertThat(found.get().getRoleId()).isEqualTo(2L);
        assertThat(found.get().getRoleName()).isEqualTo("Admin");
        assertThat(found.get().getFullName()).isEqualTo("John Doe");
        assertThat(found.get().getFirstName()).isEqualTo("John");
        assertThat(found.get().getLastName()).isEqualTo("Doe");
        assertThat(found.get().getMiddleName()).isEqualTo("M");
        assertThat(found.get().getScreenName()).isEqualTo("johnny");
        assertThat(found.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldFindAllUsers() {
        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldDeleteUser() {
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isNotPresent();
    }
}
