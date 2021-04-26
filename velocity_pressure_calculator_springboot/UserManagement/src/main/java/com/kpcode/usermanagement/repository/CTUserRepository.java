package com.kpcode.usermanagement.repository;

import com.kpcode.usermanagement.model.CTUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CTUserRepository extends JpaRepository<CTUser,Long> {
}
