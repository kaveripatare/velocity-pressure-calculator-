package com.kpcode.reportingapp.controller;

import com.kpcode.reportingapp.model.UserCalculationDto;
import com.kpcode.reportingapp.service.JasperReportService;
import com.kpcode.reportingapp.service.UserCalculationsService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

/**
 * @Author kaveri
 * @create 12/04/21
 */

@Controller
public class UserCalculationController {

    Logger logger = LoggerFactory.getLogger(UserCalculationController.class);

    @Autowired
    private UserCalculationsService userCalculationsService;

    @Autowired
    JasperReportService jasperReportService;

    @ModelAttribute("userCalculationDto")
    public UserCalculationDto userCalculationDto() {
        return new UserCalculationDto();
    }

    /**
     * show calculation page after successful login of admin
     *
     * @return
     */
    @GetMapping("/")
    public String showUserCalculationsPage() {
        return "calculations";
    }

    /**
     * show access denied page when user with no Admin Access try to login
     *
     * @return
     */
    @GetMapping("/access_denied")
    public String showAccessDeniedPage() {
        return "access_denied";
    }

    /**
     * After submitting from calculation page
     *
     * @param userCalculationDto
     * @return
     */
    @PostMapping("/calculations")
    public String calculateQzValue(@ModelAttribute("userCalculationDto") UserCalculationDto userCalculationDto) {
        userCalculationsService.calculateVelocityPressure(userCalculationDto);
        return "calculations";
    }

    /**
     * generating reports for specified formats
     *
     * @param format
     * @param model
     * @return
     * @throws FileNotFoundException
     * @throws JRException
     */
    @GetMapping("/calculations/{format}")
    public String generateReport(@PathVariable String format, Model model) throws FileNotFoundException, JRException {
        String message = jasperReportService.exportReport(format);
        model.addAttribute("message", message);
        return "calculations";

    }

    /**
     * download json output file with calculated Qz value
     *
     * @param qz
     * @param model
     * @return
     */
    @GetMapping("/calculations/json/{qz}")
    public String generateReport(@PathVariable double qz, Model model) {
        String message = userCalculationsService.saveJsonFile(qz);
        model.addAttribute("message", message);
        logger.info(message);
        return "calculations";

    }

}
