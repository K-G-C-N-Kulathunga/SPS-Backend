package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "GLDEPTIN")
public class Gldeptin {
    @Id
    @Column(name = "DEPT_ID", nullable = false, length = 6)
    private String deptId;

    @Column(name = "DEPT_TYPE", length = 10)
    private String deptType;

    @Column(name = "DEPT_AREA", length = 100)
    private String deptArea;

    @Column(name = "DEPT_PROVINCE", length = 20)
    private String deptProvince;

    @Column(name = "DEPT_REGION", length = 20)
    private String deptRegion;

    @Column(name = "DEPT_FULL_NAME", length = 50)
    private String deptFullName;

    @Column(name = "DEPT_ADD", length = 200)
    private String deptAdd;

    @Column(name = "DEPT_TEL", length = 10)
    private String deptTel;

    @Column(name = "BULK_SUPPLIER_NAME", length = 100)
    private String bulkSupplierName;

    @Column(name = "BULK_SUPPLIER_ADD", length = 200)
    private String bulkSupplierAdd;

    @Column(name = "BULK_SUPPLIER_TEL")
    private Long bulkSupplierTel;

    @Column(name = "BRANCH_CODE", length = 3)
    private String branchCode;

    @Column(name = "BANK_CODE", length = 4)
    private String bankCode;

    @Column(name = "POS_CENTER", length = 6)
    private String posCenter;

    @Column(name = "POS_A", length = 5)
    private String posA;

    @Column(name = "DEPT_CODE", length = 1)
    private String deptCode;

    @Column(name = "AREA_EE_NO", length = 10)
    private String areaEeNo;

    @Column(name = "AREA_CE_NO", length = 10)
    private String areaCeNo;

    @Column(name = "EMAIL_NO", length = 30)
    private String emailNo;

    @ColumnDefault("'Y'")
    @Column(name = "IS_PIV1_NEEDED_DEFAULT")
    private Boolean isPiv1NeededDefault;

    @Column(name = "ES_MOBILE", length = 10)
    private String esMobile;

    @Column(name = "AREA_CODE", length = 3)
    private String areaCode;

}