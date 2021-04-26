package com.kpcode.reportingapp.repository;

import com.kpcode.reportingapp.model.UserCalculationDto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

/**
 * @Author kaveri
 * @create 13/04/21
 */
public interface UsercalculationDtoRepository extends JpaRepository<UserCalculationDto, Id> {
}
