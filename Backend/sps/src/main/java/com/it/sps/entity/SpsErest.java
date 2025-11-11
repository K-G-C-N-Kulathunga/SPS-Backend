
package com.it.sps.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;

/**
 * The persistent class for the SPSEREST database table.
 */
@Entity
@Table(name = "SPSEREST")
@NamedQuery(name = "SpsErest.findAll", query = "SELECT s FROM SpsErest s")
public class SpsErest implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SpsErestPK id;

    @Column(name = "TOTAL_LENGTH", length = 10)
    private String totalLength;

    @Column(name = "SERVICE_WIRE_TYPE", length = 10)
    private String serviceWireType;

    @Column(name = "SERVICE_LENGTH", length = 10)
    private String serviceLength;

    @Column(name = "SERVICE_WIRE_DESCR", length = 50)
    private String serviceWireDescr;

    @Column(name = "BARECON_TYPE", length = 10)
    private String bareconType;

    @Column(name = "BARECON_LENGTH", precision = 6, scale = 2)
    private BigDecimal bareconLength;

    @Column(name = "BARECON_DESCR", length = 50)
    private String bareconDescr;

    @Column(name = "CONVERSION1_QTY", precision = 6, scale = 2)
    private BigDecimal conversion1Qty;

    @Column(name = "CONVERSION1_DESCR", length = 50)
    private String conversion1Descr;

    @Column(name = "CONVERSION2_QTY", precision = 6, scale = 2)
    private BigDecimal conversion2Qty;

    @Column(name = "CONVERSION2_DESCR", length = 50)
    private String conversion2Descr;

    @Column(name = "NO_OF_SPANS", precision = 3, scale = 0)
    private BigDecimal noOfSpans;

    @Column(name = "SPANS_DESCR", length = 50)
    private String spansDescr;

    @Column(name = "SUBSTATION", length = 50)
    private String substation;

    @Column(name = "SIN", length = 5)
    private String sin;

    @Column(name = "DISTANCE_FROM_SS", precision = 6, scale = 2)
    private BigDecimal distanceFromSs;

    @Column(name = "PHASE", length = 50)
    private String phase;

    @Column(name = "TRANSFORMER_CAPACITY", length = 50)
    private String transformerCapacity;

    @Column(name = "POLENO", length = 50)
    private String poleno;

    @Column(name = "TRANSFORMER_LOAD", length = 50)
    private String transformerLoad;

    @Column(name = "TRANSFORMER_PEAK_LOAD", length = 50)
    private String transformerPeakLoad;

    @Column(name = "FEEDER_CONTROL_TYPE", length = 50)
    private String feederControlType;

    @Column(name = "WIRING_TYPE", length = 2)
    private String wiringType;

    @Column(name = "DISTANCE_TO_SP", precision = 5, scale = 2)
    private BigDecimal distanceToSp;

    @Column(name = "LOOP_CABLE", length = 5)
    private String loopCable;

    @Column(name = "CABLE_TYPE", length = 30)
    private String cableType;

    @Column(name = "CONVERSION_LENGTH", precision = 4, scale = 0)
    private BigDecimal conversionLength;

    @Column(name = "CONVERSION_LENGTH_2P", precision = 4, scale = 0)
    private BigDecimal conversionLength2p;

    @Column(name = "IS_SERVICE_CONVERSION", length = 6)
    private String isServiceConversion;

    @Column(name = "SECOND_CIRCUIT_LENGTH", precision = 4, scale = 0)
    private BigDecimal secondCircuitLength;

    @Column(name = "IS_STANDARD_VC", length = 1)
    private String isStandardVc;

    @Column(name = "ADD_USER", length = 11)
    private String addUser;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADD_DATE")
    private Date addDate;

    @Column(name = "ADD_TIME", length = 11)
    private String addTime;

    @Column(name = "UPD_USER", length = 11)
    private String updUser;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPD_DATE")
    private Date updDate;

    @Column(name = "UPD_TIME", length = 11)
    private String updTime;

    @Column(name = "INSIDE_LENGTH", precision = 4, scale = 0)
    private BigDecimal insideLength;

    @Column(name = "IS_MV_DEVELOPMENT", length = 1)
    private String isMvDevelopment;

    @Column(name = "MV_LINE_LENGTH", precision = 6, scale = 2)
    private BigDecimal mvLineLength;

    @Column(name = "MV_REQ_CAPACITY", precision = 6, scale = 2)
    private BigDecimal mvReqCapacity;

    @Column(name = "MV_TRANS_CAPACITY", precision = 10, scale = 2)
    private BigDecimal mvTransCapacity;

    @Column(name = "MV_LINE_TYPE", length = 20)
    private String mvLineType;

    @Column(name = "MV_SIZE", precision = 4, scale = 0)
    private BigDecimal mvSize;

    @Column(name = "IS_EXIST_METER", length = 1)
    private String isExistMeter;

    @Column(name = "BUSINESS_TYPE", length = 2)
    private String businessType;

    @Column(name = "IS_SYA_NEEDED", length = 1)
    private String isSyaNeeded;

    public SpsErest() {
    }

    // ----------- Getters & Setters -----------

    public SpsErestPK getId() {
        return id;
    }

    public void setId(SpsErestPK id) {
        this.id = id;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    public String getServiceWireType() {
        return serviceWireType;
    }

    public void setServiceWireType(String serviceWireType) {
        this.serviceWireType = serviceWireType;
    }

    public String getServiceLength() {
        return serviceLength;
    }

    public void setServiceLength(String serviceLength) {
        this.serviceLength = serviceLength;
    }

    public String getServiceWireDescr() {
        return serviceWireDescr;
    }

    public void setServiceWireDescr(String serviceWireDescr) {
        this.serviceWireDescr = serviceWireDescr;
    }

    public String getBareconType() {
        return bareconType;
    }

    public void setBareconType(String bareconType) {
        this.bareconType = bareconType;
    }

    public BigDecimal getBareconLength() {
        return bareconLength;
    }

    public void setBareconLength(BigDecimal bareconLength) {
        this.bareconLength = bareconLength;
    }

    public String getBareconDescr() {
        return bareconDescr;
    }

    public void setBareconDescr(String bareconDescr) {
        this.bareconDescr = bareconDescr;
    }

    public BigDecimal getConversion1Qty() {
        return conversion1Qty;
    }

    public void setConversion1Qty(BigDecimal conversion1Qty) {
        this.conversion1Qty = conversion1Qty;
    }

    public String getConversion1Descr() {
        return conversion1Descr;
    }

    public void setConversion1Descr(String conversion1Descr) {
        this.conversion1Descr = conversion1Descr;
    }

    public BigDecimal getConversion2Qty() {
        return conversion2Qty;
    }

    public void setConversion2Qty(BigDecimal conversion2Qty) {
        this.conversion2Qty = conversion2Qty;
    }

    public String getConversion2Descr() {
        return conversion2Descr;
    }

    public void setConversion2Descr(String conversion2Descr) {
        this.conversion2Descr = conversion2Descr;
    }

    public BigDecimal getNoOfSpans() {
        return noOfSpans;
    }

    public void setNoOfSpans(BigDecimal noOfSpans) {
        this.noOfSpans = noOfSpans;
    }

    public String getSpansDescr() {
        return spansDescr;
    }

    public void setSpansDescr(String spansDescr) {
        this.spansDescr = spansDescr;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public BigDecimal getDistanceFromSs() {
        return distanceFromSs;
    }

    public void setDistanceFromSs(BigDecimal distanceFromSs) {
        this.distanceFromSs = distanceFromSs;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getTransformerCapacity() {
        return transformerCapacity;
    }

    public void setTransformerCapacity(String transformerCapacity) {
        this.transformerCapacity = transformerCapacity;
    }

    public String getPoleno() {
        return poleno;
    }

    public void setPoleno(String poleno) {
        this.poleno = poleno;
    }

    public String getTransformerLoad() {
        return transformerLoad;
    }

    public void setTransformerLoad(String transformerLoad) {
        this.transformerLoad = transformerLoad;
    }

    public String getTransformerPeakLoad() {
        return transformerPeakLoad;
    }

    public void setTransformerPeakLoad(String transformerPeakLoad) {
        this.transformerPeakLoad = transformerPeakLoad;
    }

    public String getFeederControlType() {
        return feederControlType;
    }

    public void setFeederControlType(String feederControlType) {
        this.feederControlType = feederControlType;
    }

    public String getWiringType() {
        return wiringType;
    }

    public void setWiringType(String wiringType) {
        this.wiringType = wiringType;
    }

    public BigDecimal getDistanceToSp() {
        return distanceToSp;
    }

    public void setDistanceToSp(BigDecimal distanceToSp) {
        this.distanceToSp = distanceToSp;
    }

    public String getLoopCable() {
        return loopCable;
    }

    public void setLoopCable(String loopCable) {
        this.loopCable = loopCable;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    public BigDecimal getConversionLength() {
        return conversionLength;
    }

    public void setConversionLength(BigDecimal conversionLength) {
        this.conversionLength = conversionLength;
    }

    public BigDecimal getConversionLength2p() {
        return conversionLength2p;
    }

    public void setConversionLength2p(BigDecimal conversionLength2p) {
        this.conversionLength2p = conversionLength2p;
    }

    public String getIsServiceConversion() {
        return isServiceConversion;
    }

    public void setIsServiceConversion(String isServiceConversion) {
        this.isServiceConversion = isServiceConversion;
    }

    public BigDecimal getSecondCircuitLength() {
        return secondCircuitLength;
    }

    public void setSecondCircuitLength(BigDecimal secondCircuitLength) {
        this.secondCircuitLength = secondCircuitLength;
    }

    public String getIsStandardVc() {
        return isStandardVc;
    }

    public void setIsStandardVc(String isStandardVc) {
        this.isStandardVc = isStandardVc;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public BigDecimal getInsideLength() {
        return insideLength;
    }

    public void setInsideLength(BigDecimal insideLength) {
        this.insideLength = insideLength;
    }

    public String getIsMvDevelopment() {
        return isMvDevelopment;
    }

    public void setIsMvDevelopment(String isMvDevelopment) {
        this.isMvDevelopment = isMvDevelopment;
    }

    public BigDecimal getMvLineLength() {
        return mvLineLength;
    }

    public void setMvLineLength(BigDecimal mvLineLength) {
        this.mvLineLength = mvLineLength;
    }

    public BigDecimal getMvReqCapacity() {
        return mvReqCapacity;
    }

    public void setMvReqCapacity(BigDecimal mvReqCapacity) {
        this.mvReqCapacity = mvReqCapacity;
    }

    public BigDecimal getMvTransCapacity() {
        return mvTransCapacity;
    }

    public void setMvTransCapacity(BigDecimal mvTransCapacity) {
        this.mvTransCapacity = mvTransCapacity;
    }

    public String getMvLineType() {
        return mvLineType;
    }

    public void setMvLineType(String mvLineType) {
        this.mvLineType = mvLineType;
    }

    public BigDecimal getMvSize() {
        return mvSize;
    }

    public void setMvSize(BigDecimal mvSize) {
        this.mvSize = mvSize;
    }

    public String getIsExistMeter() {
        return isExistMeter;
    }

    public void setIsExistMeter(String isExistMeter) {
        this.isExistMeter = isExistMeter;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getIsSyaNeeded() {
        return isSyaNeeded;
    }

    public void setIsSyaNeeded(String isSyaNeeded) {
        this.isSyaNeeded = isSyaNeeded;
    }
}
