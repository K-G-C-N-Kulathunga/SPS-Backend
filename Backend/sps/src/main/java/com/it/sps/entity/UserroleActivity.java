package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the USERROLE_ACTIVITY database table.
 * 
 */
@Entity
@Table(name="USERROLE_ACTIVITY")
@NamedQuery(name="UserroleActivity.findAll", query="SELECT u FROM UserroleActivity u")
public class UserroleActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserroleActivityPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="UPD_DATE")
	private Date updDate;

	@Column(name="UPD_USER")
	private String updUser;

	//bi-directional many-to-one association to Activity
	@ManyToOne
	@JoinColumn(name="ACTIVITY_TYPE")
	private Activity activity;

	//bi-directional many-to-one association to Userrole
	@ManyToOne
	@JoinColumn(name="ROLE_TYPE")
	private Userrole userrole;

	public UserroleActivity() {
	}

	public UserroleActivityPK getId() {
		return this.id;
	}

	public void setId(UserroleActivityPK id) {
		this.id = id;
	}

	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdUser() {
		return this.updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Userrole getUserrole() {
		return this.userrole;
	}

	public void setUserrole(Userrole userrole) {
		this.userrole = userrole;
	}

}