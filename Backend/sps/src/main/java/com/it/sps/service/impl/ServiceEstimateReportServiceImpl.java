package com.it.sps.service.impl;

import com.it.sps.entity.SpsErest;
import com.it.sps.service.ServiceEstimateReportService;
import com.it.sps.service.SpsErestService;
import com.it.sps.service.SpsetpolService;
import com.it.sps.service.SpsetstuService;
import com.it.sps.service.SpsetstyService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class ServiceEstimateReportServiceImpl implements ServiceEstimateReportService {

    private final SpsErestService spsErestService;
    private final SpsetpolService spsetpolService;
    private final SpsetstuService spsetstuService;
    private final SpsetstyService spsetstyService;

    public ServiceEstimateReportServiceImpl(SpsErestService spsErestService,
                                            SpsetpolService spsetpolService,
                                            SpsetstuService spsetstuService,
                                            SpsetstyService spsetstyService) {
        this.spsErestService = spsErestService;
        this.spsetpolService = spsetpolService;
        this.spsetstuService = spsetstuService;
        this.spsetstyService = spsetstyService;
    }

    @Override
    public byte[] generatePdf(String applicationNo, String deptId) {
        try {
            Map<String, Object> params = buildParameters(applicationNo, deptId);

            // Try compiled .jasper first; if missing, compile .jrxml on the fly
            JasperReport report;
            ClassPathResource jasperRes = new ClassPathResource("reports/service_estimate.jasper");
            if (jasperRes.exists()) {
                try (InputStream jasperStream = jasperRes.getInputStream()) {
                    report = (JasperReport) JRLoader.loadObject(jasperStream);
                } catch (Exception loadErr) {
                    // Fallback to compiling JRXML if compiled binary cannot be loaded
                    ClassPathResource jrxmlRes = new ClassPathResource("reports/service_estimate.jrxml");
                    if (!jrxmlRes.exists()) {
                        throw new IllegalStateException("Failed to load compiled Jasper (" + loadErr.getMessage() + ") and JRXML not found: reports/service_estimate.jrxml");
                    }
                    try (InputStream jrxmlStream = jrxmlRes.getInputStream()) {
                        JasperDesign design = JRXmlLoader.load(jrxmlStream);
                        report = JasperCompileManager.compileReport(design);
                    }
                }
            } else {
                ClassPathResource jrxmlRes = new ClassPathResource("reports/service_estimate.jrxml");
                if (!jrxmlRes.exists()) {
                    throw new IllegalStateException("Report template not found: reports/service_estimate.jasper or .jrxml");
                }
                try (InputStream jrxmlStream = jrxmlRes.getInputStream()) {
                    JasperDesign design = JRXmlLoader.load(jrxmlStream);
                    report = JasperCompileManager.compileReport(design);
                }
            }

            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource(1));
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Service Estimate PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> buildParameters(String applicationNo, String deptId) {
        // Fetch primary estimate
        SpsErest est;
        try {
            est = spsErestService.findOne(applicationNo, deptId);
        } catch (Exception notFound) {
            est = new SpsErest();
            est.setId(new com.it.sps.entity.SpsErestPK(applicationNo, deptId));
        }

        // Collections for tables (Poles/Struts/Stays)
        List<?> poles = safeList(spsetpolService.findByApplicationAndDept(applicationNo, deptId));
        List<?> struts = safeList(spsetstuService.findByApplicationAndDept(applicationNo, deptId));
        List<?> stays = safeList(spsetstyService.findByApplicationAndDept(applicationNo, deptId));

        Map<String, Object> params = new HashMap<>();
        // Simple scalar params used in the JRXML via $P{...}
        params.put("APPLICATION_NO", applicationNo);
        params.put("DEPT_ID", deptId);
        params.put("TOTAL_LENGTH", nullSafe(est.getTotalLength()));
        params.put("WIRING_TYPE", nullSafe(est.getWiringType()));
        params.put("LOOP_CABLE", nullSafe(est.getLoopCable()));
        params.put("INSIDE_LENGTH", nullSafe(est.getInsideLength()));
        params.put("DISTANCE_TO_SP", nullSafe(est.getDistanceToSp()));
        params.put("SIN", nullSafe(est.getSin()));
        params.put("BUSINESS_TYPE", nullSafe(est.getBusinessType()));
        params.put("NO_OF_SPANS", nullSafe(est.getNoOfSpans()));
        params.put("POLE_NO", nullSafe(est.getPoleno()));
        params.put("DISTANCE_FROM_SS", nullSafe(est.getDistanceFromSs()));
        params.put("SUBSTATION", nullSafe(est.getSubstation()));
        params.put("TRANSFORMER_CAPACITY", nullSafe(est.getTransformerCapacity()));
        params.put("TRANSFORMER_LOAD", nullSafe(est.getTransformerLoad()));
        params.put("TRANSFORMER_PEAK_LOAD", nullSafe(est.getTransformerPeakLoad()));
        params.put("FEEDER_CONTROL_TYPE", nullSafe(est.getFeederControlType()));
        params.put("PHASE", nullSafe(est.getPhase()));
        params.put("PRINTED_AT", new Date());

        // Table datasets as JRBeanCollectionDataSource (referenced in JRXML via DatasetRun/parameter)
        params.put("POL_LIST", new JRBeanCollectionDataSource(poles));
        params.put("STRUT_LIST", new JRBeanCollectionDataSource(struts));
        params.put("STAY_LIST", new JRBeanCollectionDataSource(stays));

        return params;
    }

    private static String nullSafe(Object v) {
        return v == null ? "" : String.valueOf(v);
    }

    private static List<?> safeList(List<?> l) {
        return l == null ? Collections.emptyList() : l;
    }
}
