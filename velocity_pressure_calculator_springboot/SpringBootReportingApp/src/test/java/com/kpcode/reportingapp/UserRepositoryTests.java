package com.kpcode.reportingapp;

/**
 * @Author kaveri
 * @create 12/04/21
 */
import static org.assertj.core.api.Assertions.assertThat;

import com.kpcode.reportingapp.model.User;
import com.kpcode.reportingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserRepository repo;

    // test method for creating user
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("ravisharma@gmail.com");
        user.setPassword("ravi2020");
        user.setName("Ravi Sharma");
        user.setEnabled(true);

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}
