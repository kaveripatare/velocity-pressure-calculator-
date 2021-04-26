package com.kpcode.reportingapp.repository;


import com.kpcode.reportingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author kaveri
 * @create 11/04/21
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
