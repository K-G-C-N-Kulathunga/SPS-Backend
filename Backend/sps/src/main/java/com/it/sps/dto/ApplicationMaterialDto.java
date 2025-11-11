package com.it.sps.dto;


public class ApplicationMaterialDto {
	

	private String deptId;
	private String wiringType;
	private long connectionType;
	private long phase;
//	private List<MaterialDTO> materialList;
	
	public ApplicationMaterialDto(String deptId, String wiringType, long connectionType, long phase ) {
		this.deptId = deptId;
		this.wiringType = wiringType;
		this.connectionType = connectionType;
		this.phase = phase;
//		this.materialList = materialList;
	}

	public ApplicationMaterialDto() {	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getWiringType() {
		return wiringType;
	}

	public void setWiringType(String wiringType) {
		this.wiringType = wiringType;
	}

	public long getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(long connectionType) {
		this.connectionType = connectionType;
	}

	public long getPhase() {
		return phase;
	}

	public void setPhase(long phase) {
		this.phase = phase;
	}

	
	
	
	
	
	
}