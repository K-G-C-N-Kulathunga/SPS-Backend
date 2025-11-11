package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SPESTLAB database table.
 * 
 */
@Entity
@NamedQuery(name="Spestlab.findAll", query="SELECT s FROM Spestlab s")
public class Spestlab implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpestlabPK id;

	@Column(name="ACTIVITY_DESCRIPTION")
	private String activityDescription;

	@Column(name="CEB_LABOUR_COST")
	private BigDecimal cebLabourCost;

	@Column(name="CEB_UNIT_PRICE")
	private BigDecimal cebUnitPrice;

	private BigDecimal distance;

	@Column(name="ITEM_QTY")
	private BigDecimal itemQty;

	@Column(name="LABOUR_COST")
	private BigDecimal labourCost;

	@Column(name="LABOUR_HOURS")
	private BigDecimal labourHours;

	private String status;

	@Column(name="UNIT_LABOUR_HRS")
	private BigDecimal unitLabourHrs;

	@Column(name="UNIT_PRICE")
	private BigDecimal unitPrice;

	public Spestlab() {
	}

	public SpestlabPK getId() {
		return this.id;
	}

	public void setId(SpestlabPK id) {
		this.id = id;
	}

	public String getActivityDescription() {
		return this.activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public BigDecimal getCebLabourCost() {
		return this.cebLabourCost;
	}

	public void setCebLabourCost(BigDecimal cebLabourCost) {
		this.cebLabourCost = cebLabourCost;
	}

	public BigDecimal getCebUnitPrice() {
		return this.cebUnitPrice;
	}

	public void setCebUnitPrice(BigDecimal cebUnitPrice) {
		this.cebUnitPrice = cebUnitPrice;
	}

	public BigDecimal getDistance() {
		return this.distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public BigDecimal getItemQty() {
		return this.itemQty;
	}

	public void setItemQty(BigDecimal itemQty) {
		this.itemQty = itemQty;
	}

	public BigDecimal getLabourCost() {
		return this.labourCost;
	}

	public void setLabourCost(BigDecimal labourCost) {
		this.labourCost = labourCost;
	}

	public BigDecimal getLabourHours() {
		return this.labourHours;
	}

	public void setLabourHours(BigDecimal labourHours) {
		this.labourHours = labourHours;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUnitLabourHrs() {
		return this.unitLabourHrs;
	}

	public void setUnitLabourHrs(BigDecimal unitLabourHrs) {
		this.unitLabourHrs = unitLabourHrs;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}