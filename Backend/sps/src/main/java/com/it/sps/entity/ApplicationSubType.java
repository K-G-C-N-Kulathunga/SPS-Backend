package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "APPLICATION_SUBTYPE")
public class ApplicationSubType {

    @Id
    @Column(name = "APP_SUBTYPE_CODE", length = 50, nullable = false)
    private String appSubTypeCode;

    @Column(name = "APP_SUBTYPE_NAME", length = 100)
    private String appSubTypeName;

    @Column(name = "APP_TYPE_CODE", length = 50, nullable = false)
    private String appTypeCode;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "ORDER_KEY")
    private Integer orderKey;

    @Column(name = "UPD_USER", length = 10)
    private String updUser;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPD_DATE")
    private Date updDate;
}
