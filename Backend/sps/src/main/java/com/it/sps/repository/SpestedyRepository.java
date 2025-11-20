package com.it.sps.repository;

import com.it.sps.entity.Spestedy;
import com.it.sps.entity.SpestedyId;
import com.it.sps.dto.AppointmentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpestedyRepository extends JpaRepository<Spestedy, SpestedyId> {

//    @Query(value = """
//                    SELECT
//                        s.APPOINTMENT_ID AS appointmentId,
//                        s.DEPT_ID AS deptId,
//                        s.REFERENCE_NO AS applicationId,
//                        TO_CHAR(s.APPOINTMENT_DATE, 'YYYY-MM-DD') AS appointmentDate,
//                        s.TIME_SESSION AS timeSession,
//                        s.DESCRIPTION AS description,
//                        COALESCE(app.CONTACT_MOBILE, ap.MOBILE_NO) AS phone,
//                        ap.FULL_NAME AS name,
//                        w.SERVICE_STREET_ADDRESS || ', ' || w.SERVICE_SUBURB || ', ' || w.SERVICE_CITY AS address
//                    FROM SPESTEDY s
//                    LEFT JOIN APPLICATIONS app ON s.REFERENCE_NO = app.APPLICATION_ID AND s.DEPT_ID = app.DEPT_ID
//                    LEFT JOIN WIRING_LAND_DETAIL w ON w.APPLICATION_ID = app.APPLICATION_ID AND w.DEPT_ID = app.DEPT_ID
//                    LEFT JOIN APPLICANT ap ON ap.ID_NO = app.ID_NO
//            """, nativeQuery = true)
//    List<Object[]> findAllAppointmentsJoined();

//    @Query(value = """
//                SELECT
//                    s.APPOINTMENT_ID AS appointmentId,
//                    s.DEPT_ID AS deptId,
//                    s.REFERENCE_NO AS applicationId,
//                    TO_CHAR(s.APPOINTMENT_DATE, 'YYYY-MM-DD') AS appointmentDate,
//                    s.TIME_SESSION AS timeSession,
//                    s.DESCRIPTION AS description,
//                    COALESCE(app.CONTACT_MOBILE, ap.MOBILE_NO) AS phone,
//                    ap.FULL_NAME AS name,
//                    w.SERVICE_STREET_ADDRESS || ', ' || w.SERVICE_SUBURB || ', ' || w.SERVICE_CITY AS address
//                FROM SPESTEDY s
//                LEFT JOIN APPLICATIONS app
//                    ON s.REFERENCE_NO = app.APPLICATION_ID AND s.DEPT_ID = app.DEPT_ID
//                LEFT JOIN WIRING_LAND_DETAIL w
//                    ON w.APPLICATION_ID = app.APPLICATION_ID AND w.DEPT_ID = app.DEPT_ID
//                LEFT JOIN APPLICANT ap
//                    ON ap.ID_NO = app.ID_NO
//                WHERE s.DEPT_ID = :deptId
//        """, nativeQuery = true)
//    List<Object[]> findAppointmentsByDept(String deptId);


    @Query(value = """
    SELECT
        s.APPOINTMENT_ID AS appointmentId,
        s.DEPT_ID AS deptId,
        s.REFERENCE_NO AS applicationId,
        TO_CHAR(s.APPOINTMENT_DATE, 'YYYY-MM-DD') AS appointmentDate,
        s.TIME_SESSION AS timeSession,
        s.DESCRIPTION AS description,
        COALESCE(app.CONTACT_MOBILE, ap.MOBILE_NO) AS phone,
        ap.FULL_NAME AS name,
        w.SERVICE_STREET_ADDRESS || ', ' || w.SERVICE_SUBURB || ', ' || w.SERVICE_CITY AS address,
        oa.LATITUDE AS latitude,
        oa.LONGITUDE AS longitude
    FROM SPESTEDY s
    LEFT JOIN APPLICATIONS app 
        ON s.REFERENCE_NO = app.APPLICATION_ID AND s.DEPT_ID = app.DEPT_ID
    LEFT JOIN WIRING_LAND_DETAIL w 
        ON w.APPLICATION_ID = app.APPLICATION_ID AND w.DEPT_ID = app.DEPT_ID
    LEFT JOIN APPLICANT ap 
        ON ap.ID_NO = app.ID_NO
    LEFT JOIN ONLINE_APPLICATION oa
        ON oa.APPLICATION_NO = app.APPLICATION_NO
        -- optionally also filter by dept if needed:
        -- AND TRIM(oa.DEPT_ID) = app.DEPT_ID
    WHERE s.DEPT_ID = :deptId
    """, nativeQuery = true)
    List<Object[]> findAppointmentsByDept(String deptId);


    @Query(value = """
        SELECT MAX(APPOINTMENT_ID)
        FROM SPESTEDY
        WHERE APPOINTMENT_ID LIKE :prefix || '%'
        """, nativeQuery = true)
    String findLatestAppointmentId(String prefix);




}




