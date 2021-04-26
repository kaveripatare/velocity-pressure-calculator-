package com.kpcode.reportingapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author kaveri
 * @create 11/04/21
 */

@Entity
@Table(name = "calculation")
public class UserCalculationDto {

    private String unit = "SI";   // we are considering default unit as SI
    private double height = 70.0;  // height to be considered as 70.0 meter
    private double kdValue = 0.0;
    private double kzValue = 1.25;  //calculated this value according to the given table
    private double kztValue = 0.0;
    private double vValue = 0.0;
    private double qzValue = 0.0;
    private String jsonOutput = "";
    private Long id;
    public String message = "";

    public UserCalculationDto() {
    }

    public UserCalculationDto(String unit, double height, double kzValue, double kztValue, double kdValue, double vValue, double qzValue, String jsonOutput, Long id) {
        this.unit = unit;
        this.height = height;
        this.kzValue = kzValue;
        this.kztValue = kztValue;
        this.kdValue = kdValue;
        this.vValue = vValue;
        this.qzValue = qzValue;
        this.jsonOutput = jsonOutput;
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getKzValue() {
        return kzValue;
    }

    public void setKzValue(double kzValue) {
        this.kzValue = kzValue;
    }

    public double getKztValue() {
        return kztValue;
    }

    public void setKztValue(double kztValue) {
        this.kztValue = kztValue;
    }

    public double getKdValue() {
        return kdValue;
    }

    public void setKdValue(double kdValue) {
        this.kdValue = kdValue;
    }

    public double getvValue() {
        return vValue;
    }

    public void setvValue(double vValue) {
        this.vValue = vValue;
    }

    public String getJsonOutput() {
        return jsonOutput;
    }

    public void setJsonOutput(String jsonOutput) {
        this.jsonOutput = jsonOutput;
    }

    public double getQzValue() {
        return qzValue;
    }

    public void setQzValue(double qzValue) {
        this.qzValue = qzValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
