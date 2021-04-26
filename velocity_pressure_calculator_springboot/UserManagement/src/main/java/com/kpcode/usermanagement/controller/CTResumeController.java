package com.kpcode.usermanagement.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.kpcode.usermanagement.bean.CTResponse;
import com.kpcode.usermanagement.model.CTResume;
import com.kpcode.usermanagement.model.CTUser;
import com.kpcode.usermanagement.service.CTResumeService;
import com.kpcode.usermanagement.service.CTUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author kaveri
 * @create 09/04/21
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/resumes")
public class CTResumeController {
    Logger logger = LoggerFactory.getLogger(CTResumeController.class);

    @Autowired
    private CTResumeService ctResumeService;

    @Autowired
    private CTUserService ctUserService;

    /**
     * Get All Resumes
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CTResume>> getAllResumes() {
        CTResponse<List<CTResume>> response = new CTResponse<>();
        try {
            response.setMessage("Data received");
            response.setStatus("Success");
            response.setResponseData(ctResumeService.findAll());
            return (ResponseEntity<List<CTResume>>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<List<CTResume>>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add Resume
     * Option 1: We are getting MultipartFile image and converting into byte[]
     * Option 2: Here we can also store image URL instead of storing byte[] into MySQL
     *
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<CTResume> createResume(@Valid @RequestParam("title") String title,
                                                 @RequestParam("user_id") Long userId,
                                                 @RequestParam("skills") String skills,
                                                 @RequestParam("photo") MultipartFile file) {
        CTResponse<CTResume> response = new CTResponse<>();
        try {
            CTResume ctResume = new CTResume();
            ctResume.setTitle(title);
            CTUser ctUser = ctUserService.findById(userId)
                    .orElse(new CTUser());

            // check whether user id exists or not
            if (!ctUserService.findById(userId).isPresent()) {
                response.setMessage("Please use existing User Id.");
                response.setStatus("Error");
                return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
            ctResume.setUser(ctUser);
            ctResume.setSkills(skills);
            ctResume.setPhoto(file.getBytes());
            log.info("image bytes---------------     " + ctResume.getPhoto());

            response.setMessage("Resume Created");
            response.setStatus("Success");
            response.setResponseData(ctResumeService.save(ctResume));
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get particular resume by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CTResume> getResume(@PathVariable Long id) {
        CTResponse<Optional<CTResume>> response = new CTResponse<>();
        try {
            Optional<CTResume> resume = ctResumeService.findById(id);

            if (!resume.isPresent()) {
                log.error("Id " + id + " is not exists");
                response.setMessage("Resume not present.");
                response.setStatus("Error");
                return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.NO_CONTENT);
            }

            response.setMessage("Data received");
            response.setStatus("Success");
            response.setResponseData(ctResumeService.findById(id));
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update particular resume
     *
     * @param id
     * @param ctResume
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CTResume> updateResume(@PathVariable Long id, @Valid @RequestBody CTResume ctResume) {
        CTResponse<CTResume> response = new CTResponse<>();
        try {
            if (!ctResumeService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                response.setMessage("Previous Resume not present");
                response.setStatus("Error");
                return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.NO_CONTENT);
            }
            response.setMessage("Resume updated");
            response.setStatus("Success");
            response.setResponseData(ctResumeService.save(ctResume));
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete particular resume
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteResume(@PathVariable Long id) {
        CTResponse<CTResume> response = new CTResponse<>();
        try {
            if (!ctResumeService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                response.setMessage("No Resume present to delete");
                response.setStatus("Error");
                return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.NO_CONTENT);
            }
            response.setMessage("Deleted Resume");
            response.setStatus("Success");
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTResume>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
