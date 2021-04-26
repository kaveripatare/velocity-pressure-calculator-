package com.kpcode.reportingapp.service;

import com.kpcode.reportingapp.model.UserCalculationDto;
import com.kpcode.reportingapp.repository.UsercalculationDtoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author kaveri
 * @create 13/04/21
 */
@Service
public class JasperReportService {

    Logger logger = LoggerFactory.getLogger(JasperReportService.class);

    @Autowired
    UsercalculationDtoRepository usercalculationDtoRepository;

    /**
     * here we generate jasper reports in specified formats
     *
     * @param reportFormat
     * @return
     * @throws FileNotFoundException
     * @throws JRException
     */
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<UserCalculationDto> calculations = usercalculationDtoRepository.findAll();
        String home = System.getProperty("user.home");
        String reportFilePath = home + "/Desktop/Jasper_reports/";
        File directory = new File(reportFilePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        //load file and import it
        File file = ResourceUtils.getFile("classpath:calculations.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(calculations);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "kpcode");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        //generate pdf report
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportFilePath + "calculations.pdf");
            logger.info("PDF Report file path:: " + reportFilePath + "calculations.pdf");
            return "File saved at " + reportFilePath + "calculations.pdf";
        }

        // export report in MS Word file
        if (reportFormat.equalsIgnoreCase("word")) {
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            File exportReportFile = new File(reportFilePath + "calculations.docx");
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
            exporter.exportReport();
            logger.info("Word Report file path:: " + reportFilePath + "calculations.docx");
            return "File saved at " + exportReportFile.getAbsolutePath();
        }
        return "";
    }
}
