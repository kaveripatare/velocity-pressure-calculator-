package com.kpcode.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author kaveri
 * @create 10/04/21
 */

@Entity
@Table(name = "resume")
@Data
public class CTResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="resume_id")
    private Long resumeId;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="user_id", nullable = false)
    private CTUser user;

    @Column(name="title")
    private String title;

    @Column(name="skills")
    private String skills;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

}
