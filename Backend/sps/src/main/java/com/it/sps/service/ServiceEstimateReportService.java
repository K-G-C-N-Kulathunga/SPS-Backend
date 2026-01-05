package com.it.sps.service;

import java.util.Map;

public interface ServiceEstimateReportService {
    /**
     * Generate the Service Estimate PDF using JasperReports for the given identifiers.
     * @param applicationNo application number (can contain slashes)
     * @param deptId department id
     * @return PDF bytes
     */
    byte[] generatePdf(String applicationNo, String deptId);

    /**
     * Returns the Jasper parameter map used for report filling (for testing/debugging).
     */
    Map<String, Object> buildParameters(String applicationNo, String deptId);
}
