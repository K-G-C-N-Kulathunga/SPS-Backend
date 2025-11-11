package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the USERROLE_ACTIVITY database table.
 * 
 */
@Embeddable
public class UserroleActivityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ACTIVITY_TYPE", insertable=false, updatable=false)
	private String activityType;

	@Column(name="ROLE_TYPE", insertable=false, updatable=false)
	private String roleType;

	public UserroleActivityPK() {
	}
	public String getActivityType() {
		return this.activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getRoleType() {
		return this.roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserroleActivityPK)) {
			return false;
		}
		UserroleActivityPK castOther = (UserroleActivityPK)other;
		return 
			this.activityType.equals(castOther.activityType)
			&& this.roleType.equals(castOther.roleType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.activityType.hashCode();
		hash = hash * prime + this.roleType.hashCode();
		
		return hash;
	}
}