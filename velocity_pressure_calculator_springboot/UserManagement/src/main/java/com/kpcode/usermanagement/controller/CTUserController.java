package com.kpcode.usermanagement.controller;

import com.kpcode.usermanagement.bean.CTResponse;
import com.kpcode.usermanagement.model.CTResume;
import com.kpcode.usermanagement.model.CTUser;
import com.kpcode.usermanagement.service.CTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


/**
 * @Author kaveri
 * @create 09/04/21
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/users")
public class CTUserController {

    @Autowired
    private CTUserService ctUserService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Get All Users
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CTUser>> getAllUsers() {
        CTResponse<List<CTUser>> response = new CTResponse<>();
        try {
            response.setMessage("Data received");
            response.setStatus("Success");
            response.setResponseData(ctUserService.findAll());
            return (ResponseEntity<List<CTUser>>) new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<List<CTUser>>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add New User
     * @param ctUser
     * @return
     */
    @PostMapping
    public ResponseEntity<CTUser> createUser(@Valid @RequestBody CTUser ctUser) {
        CTResponse<CTUser> response = new CTResponse<>();
        try {
            ctUser.setPassword(passwordEncoder.encode(ctUser.getPassword()));
            response.setMessage("Data received");
            response.setStatus("Success");
            response.setResponseData(ctUserService.save(ctUser));
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get particular user by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CTUser> getUser(@PathVariable Long id) {

        CTResponse<CTUser> response = new CTResponse<>();
        try {
            Optional<CTUser> user = ctUserService.findById(id);
            if (!user.isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            response.setMessage("User Data received");
            response.setStatus("Success");
            response.setResponseData(user.get());
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update particular user
     *
     * @param id
     * @param ctUser
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CTUser> updateUser(@PathVariable Long id, @Valid @RequestBody CTUser ctUser) {
        CTResponse<CTUser> response = new CTResponse<>();
        try {
            if (!ctUserService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            response.setMessage("User updated");
            response.setStatus("Success");
            response.setResponseData(ctUserService.save(ctUser));
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete particular user
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        CTResponse<String> response = new CTResponse<>();
        try {
            if (!ctUserService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            ctUserService.deleteById(id);
            response.setMessage("User updated");
            response.setStatus("Success");
            response.setResponseData("");
            return (ResponseEntity<String>) new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setStatus("Error");
            response.setResponseData(null);
            return (ResponseEntity<CTUser>) new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
