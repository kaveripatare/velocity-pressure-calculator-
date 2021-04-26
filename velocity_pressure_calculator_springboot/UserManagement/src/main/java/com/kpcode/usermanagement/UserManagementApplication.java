package com.kpcode.usermanagement;

import com.kpcode.usermanagement.model.CTResume;
import com.kpcode.usermanagement.model.CTUser;
import com.kpcode.usermanagement.repository.CTResumeRepository;
import com.kpcode.usermanagement.repository.CTUserRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class UserManagementApplication {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${user.imagePath}")
    private String imagePath;

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * add two sample users and one resume on startup of spring boot application
     *
     * @param ctUserRepository
     * @return
     */
    @Bean
    CommandLineRunner init(CTUserRepository ctUserRepository, CTResumeRepository ctResumeRepository) {
        return args -> {
            ctUserRepository.deleteAll();
            ctResumeRepository.deleteAll();

            CTUser ctUser1 = loadUserData("Ashish Sharma", "SB Road", "Pune", "411013", "ashish.s@gmail.com", "1111111111",
                    "123456", true, true, "ADMIN");

            CTUser ctUser2 = loadUserData("Rachana Joshi", "KT Road", "Delhi", "411015", "rachana.j@gmail.com", "2222222222",
                    "654321", false, false, "USER");

            // for sample data we are using local file path here
            // otherwise we use multipart file which is uploaded by user

            String homePath = System.getProperty("user.home");
            File file = new File(homePath + imagePath);
            byte[] bytes;
            try (InputStream inputStream = new FileInputStream(file) {
            }) {
                bytes = StreamUtils.copyToByteArray(inputStream);
            }
            CTResume ctResume = loadResumeData("Software Engineer", "Java, Spring boot, MySql", 1L, bytes);

            ctUserRepository.save(ctUser1);
            ctUserRepository.save(ctUser2);

            ctUser1.getResumes().add(ctResume);
            ctResume.setUser(ctUser1);

            ctUser2.getResumes().add(ctResume);
            ctResume.setUser(ctUser2);

            ctResumeRepository.save(ctResume);
        };


    }

    /**
     * load user data into CTUser
     *
     * @param name
     * @param address
     * @param city
     * @param pin
     * @param email
     * @param phone
     * @param password
     * @param isAdmin
     * @return CTUser
     */
    CTUser loadUserData(String name, String address, String city, String pin, String email, String phone, String password, boolean isAdmin, boolean enabled, String
            role) {
        CTUser ctUser = new CTUser();
        ctUser.setName(name);
        ctUser.setAddress(address);
        ctUser.setCity(city);
        ctUser.setPin(pin);
        ctUser.setEmail(email);
        ctUser.setPhone(phone);
        ctUser.setPassword(passwordEncoder.encode(password));
        ctUser.setAdmin(isAdmin);
        ctUser.setEnabled(enabled);
        ctUser.setRole(role);
        return ctUser;
    }

    /**
     * load resume data
     */
    CTResume loadResumeData(String title, String skills, Long userId, byte[] photo) {
        CTResume ctResume = new CTResume();
        ctResume.setSkills(skills);
        ctResume.setTitle(title);
        ctResume.setPhoto(photo);
        return ctResume;
    }
}
