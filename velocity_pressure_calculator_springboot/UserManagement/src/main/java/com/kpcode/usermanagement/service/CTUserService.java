package com.kpcode.usermanagement.service;

import com.kpcode.usermanagement.model.CTUser;
import com.kpcode.usermanagement.repository.CTUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author kaveri
 * @create 09/04/21
 */

@Service
public class CTUserService {

    @Autowired
    private CTUserRepository ctUserRepository;

    public List<CTUser> findAll() {
        return ctUserRepository.findAll();
    }

    public Optional<CTUser> findById(Long id) {
        return ctUserRepository.findById(id);
    }

    public CTUser save(CTUser ctUser) {
        return ctUserRepository.save(ctUser);
    }

    public void deleteById(Long id) {
        ctUserRepository.deleteById(id);

    }
}
