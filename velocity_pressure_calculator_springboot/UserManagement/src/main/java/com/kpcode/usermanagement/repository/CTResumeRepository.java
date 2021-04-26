package com.kpcode.usermanagement.repository;

import com.kpcode.usermanagement.model.CTResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CTResumeRepository extends JpaRepository<CTResume,Long> {
}
