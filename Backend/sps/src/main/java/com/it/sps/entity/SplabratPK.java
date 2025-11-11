package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the SPLABRAT database table.
 * 
 */
@Embeddable
public class SplabratPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LABOUR_CODE")
	private String labourCode;

	@Column(name="DEPT_ID", insertable=false, updatable=false)
	private String deptId;

	@Column(name="YEAR")
	private String year;

	public SplabratPK() {
	}
	public String getLabourCode() {
		return this.labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public String getDeptId() {
		return this.deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SplabratPK)) {
			return false;
		}
		SplabratPK castOther = (SplabratPK)other;
		return 
			this.labourCode.equals(castOther.labourCode)
			&& this.deptId.equals(castOther.deptId)
			&& this.year.equals(castOther.year);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.labourCode.hashCode();
		hash = hash * prime + this.deptId.hashCode();
		hash = hash * prime + this.year.hashCode();
		
		return hash;
	}
}