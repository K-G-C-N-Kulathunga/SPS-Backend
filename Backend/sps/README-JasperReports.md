# Jaspersoft Studio → Spring Boot PDF Printing

This repo is already wired for JasperReports. Follow these steps to design your report in Jaspersoft Studio and print as inline PDF from the backend.

## What you need
- Jaspersoft Studio (Windows) — you already installed
- This backend running (Spring Boot)
- Optional: Frontend (React) that calls the print endpoint

## Design in Jaspersoft Studio
1. Open Jaspersoft Studio and select your workspace folder. Example from your screenshot:
   - `C:/Users/User/JaspersoftWorkspace/MyReports`
2. Create a new report:
   - File → New → Jasper Report → choose template (e.g., Blank A4)
   - Name: `service_estimate`
3. Define Parameters (right panel → Parameters → `+`):
   Add these exact names (type `java.lang.String` unless noted):
   - `APPLICATION_NO`
   - `DEPT_ID`
   - `TOTAL_LENGTH`
   - `WIRING_TYPE`
   - `LOOP_CABLE`
   - `INSIDE_LENGTH`
   - `DISTANCE_TO_SP`
   - `SIN`
   - `BUSINESS_TYPE`
   - `NO_OF_SPANS`
   - `POLE_NO`
   - `DISTANCE_FROM_SS`
   - `SUBSTATION`
   - `TRANSFORMER_CAPACITY`
   - `TRANSFORMER_LOAD`
   - `TRANSFORMER_PEAK_LOAD`
   - `FEEDER_CONTROL_TYPE`
   - `PHASE`
   - `PRINTED_AT` (type `java.util.Date`)

4. Optional tables for lists (Poles/Struts/Stays):
   - Create three table components (Palette → Table) for `POL_LIST`, `STRUT_LIST`, `STAY_LIST`.
   - In each table’s Dataset Run → Parameters:
     - Add a parameter with name matching the list: `POL_LIST` (or `STRUT_LIST`, `STAY_LIST`).
     - Expression: `new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($P{POL_LIST})`
   - Add columns whose Field expressions map to your bean properties:
     - Example: `$F{poleType}`, `$F{count}`, `$F{distance}`. Use your actual entity field names (`Spsetpol`, `Spsetstu`, `Spsetsty`).

5. Layout the report:
   - Use Text Fields with expressions like `$P{APPLICATION_NO}`, `$P{DEPT_ID}`, etc.
   - Add headers/footers, logo, page numbers as needed.

6. Compile / export:
   - File → Save → ensures `.jrxml` exists.
   - Right-click the report → Compile Report → generates `.jasper` (recommended for production).

## Copy template into backend
Backend loads the template from the classpath:
- Path: `SPS-Backend/Backend/sps/src/main/resources/reports/`
- Copy one of these:
  - `service_estimate.jasper` (preferred), or
  - `service_estimate.jrxml` (backend can compile at runtime)

Note: The repo already contains a minimal `reports/service_estimate.jrxml`. Replace it with your Studio version or add the compiled `.jasper` alongside it.

## Backend wiring (already done)
- Service: `ServiceEstimateReportServiceImpl` builds parameters and lists, compiles/loads the report, exports PDF.
- Endpoint: `GET /api/applications/connection-details/service-estimate/print?applicationNo=...&deptId=...`
  - Returns `application/pdf` with `Content-Disposition: inline` (prints in a hidden iframe).

## Quick run (Windows)
From the backend folder `SPS-Backend/Backend/sps`:
```bat
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```
Backend runs on your configured port (commonly `9090`).

## Test the print
- Browser URL:
  - `http://localhost:9090/api/applications/connection-details/service-estimate/print?applicationNo=APP/123&deptId=ELEC`
- Curl:
```powershell
curl -o service_estimate.pdf "http://localhost:9090/api/applications/connection-details/service-estimate/print?applicationNo=APP/123&deptId=ELEC"
```

## Frontend (hidden iframe) print
Your React code should request the endpoint as a `blob`, create an object URL, set it on a hidden iframe, and call `print()`. This is already implemented in your `serviceestimatedetails.js`.

## Parameter/dataset contract
- Parameters set by backend (already implemented):
  - Scalars: see list above.
  - Lists: `POL_LIST`, `STRUT_LIST`, `STAY_LIST` as `JRBeanCollectionDataSource`.
- In Studio, reference scalars via `$P{PARAM_NAME}` and bind tables via Dataset Run using the matching names.

## Common tips
- Fonts: If text looks different in PDF, embed fonts or use common fonts (e.g., Arial). In Studio, configure Font Extensions if needed.
- Images: Use Classpath resources or absolute paths. For classpath, place under `src/main/resources` and reference with `$P{REPORT_DIR}` if you add one.
- Page size: A4 portrait is `595×842` points; use margins appropriately.
- Recompile on changes: After editing `.jrxml`, recompile to `.jasper` and copy it again.

## Troubleshooting
- 500 error: Ensure the template exists under `src/main/resources/reports/` and parameter names match exactly.
- Empty tables: Confirm entity fields match the column expressions and that lists aren’t empty for the given `applicationNo`/`deptId`.
- CORS/network: Backend allows preflight; call the endpoint from your frontend with correct base URL.

---
Need help mapping your table fields (`Spsetpol`, `Spsetstu`, `Spsetsty`)? Tell me the property names you want in each column and I’ll update the JRXML accordingly.