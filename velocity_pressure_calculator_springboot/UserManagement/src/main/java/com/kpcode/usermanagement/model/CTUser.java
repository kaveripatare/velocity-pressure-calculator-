package com.kpcode.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author kaveri
 * @create 10/04/21
 */

@Entity
@Table(name = "user", uniqueConstraints =@UniqueConstraint(columnNames = "email") )
@Data
public class CTUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CTResume> resumes =new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "pin")
    private String pin;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    private String role;
}
