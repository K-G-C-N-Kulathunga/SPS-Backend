package com.it.sps.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.FileInputStream;

import javax.sql.DataSource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationReportService {

    @Autowired
    private DataSource dataSource;

    @Value("${reports.path:./reports}")
    private String reportsPath;

    public void generateApplicationPdf(String applicationNo,HttpServletResponse response) throws Exception {

        String reportName = "CEB_Application_Report.jrxml";
        InputStream reportStream = null;

        File externalReport = new File(reportsPath + "/" + reportName);
        if (!externalReport.exists()) {
            throw new RuntimeException("❌ ERROR: Could not find '" + reportName + "' at: " + externalReport.getAbsolutePath());
        }

        reportStream = new FileInputStream(externalReport);
        System.out.println("✓ Loading report from: " + externalReport.getAbsolutePath());

        // 2. Compile
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 3. Parameters
        Map<String, Object> params = new HashMap<>();
        params.put("inp_appid", applicationNo);

        // 4. Generate PDF
        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            // 5. Check if data was found (Optional: prevents blank PDFs)
            if (jasperPrint.getPages().isEmpty()) {
                throw new RuntimeException("❌ Report generated successfully, but it is BLANK (No pages). Check your SQL query or parameters.");
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Application_" + applicationNo + ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            exporter.exportReport();

            response.getOutputStream().flush();
        }
    }
}