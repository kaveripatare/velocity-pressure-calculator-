package com.kpcode.usermanagement.service;

import com.kpcode.usermanagement.model.CTResume;
import com.kpcode.usermanagement.repository.CTResumeRepository;
import com.kpcode.usermanagement.repository.CTResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author kaveri
 * @create 09/04/21
 */

@Service
public class CTResumeService {

    @Autowired
    private CTResumeRepository ctResumeRepository;

    public List<CTResume> findAll() {
        return ctResumeRepository.findAll();
    }

    public Optional<CTResume> findById(Long id) {
        return ctResumeRepository.findById(id);
    }

    public CTResume save(CTResume stock) {
        return ctResumeRepository.save(stock);
    }

    public void deleteById(Long id) {
        ctResumeRepository.deleteById(id);
    }

}
