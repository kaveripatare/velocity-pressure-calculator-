package com.kpcode.reportingapp.service;

import com.kpcode.reportingapp.model.UserCalculationDto;
import com.kpcode.reportingapp.repository.UsercalculationDtoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author kaveri
 * @create 12/04/21
 */

@Service
public class UserCalculationsService {

    Logger logger = LoggerFactory.getLogger(UserCalculationsService.class);


    @Autowired
    UsercalculationDtoRepository usercalculationDtoRepository;

    /**
     * Velocity Pressure (Qz) calculations
     * @param userCalculationDto
     */
    public void calculateVelocityPressure(UserCalculationDto userCalculationDto) {
        double factor = 0.613, qz;

        if (userCalculationDto.getUnit().equalsIgnoreCase("metric")) {
            factor = 0.00256;
        }
        if (userCalculationDto.getUnit().equalsIgnoreCase("si")) {
            factor = 0.613;
        }
        qz = factor * userCalculationDto.getKzValue() * userCalculationDto.getKztValue()
                * userCalculationDto.getKdValue() * Math.pow(userCalculationDto.getvValue(), 2);
        qz = Math.round(qz * 100.0) / 100.0;

        logger.debug("--------------------qzValue is :: " + qz);
        userCalculationDto.setQzValue(qz);

        String qzVal = "{  \n  \"qz\" : \"" + String.valueOf(qz) + "\"" + "\n}";

        userCalculationDto.setJsonOutput(qzVal);
        userCalculationDto.setId(System.currentTimeMillis());
        usercalculationDtoRepository.save(userCalculationDto);

    }

    /**
     * Download Json File
     *
     * @param qz
     * @return
     */
    public String saveJsonFile(double qz) {

        String qzVal = "{  \n  \"qz\" : \"" + String.valueOf(qz) + "\"" + "\n}";

        String home = System.getProperty("user.home");
        String fileName = "output_" + System.currentTimeMillis() + ".json";
        File outputfile = new File(home + "/Downloads/" + fileName);
        try {
            FileWriter file = new FileWriter(outputfile);
            file.write(qzVal);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Output JSON file path :: "+outputfile.getAbsolutePath());
        return "File saved at " + outputfile.getAbsolutePath();
    }
}
