package com.it.sps.dto;

import java.util.List;

public class MaterialAndLabourDTO {
	
	private List<PcestdttDTO> pcestdttDTOs;
	private List<SpestlabDTO> spestlabDTOs;
	
	public List<PcestdttDTO> getPcestdttDTOs() {
		return pcestdttDTOs;
	}
	public void setPcestdttDTOs(List<PcestdttDTO> pcestdttDTOs) {
		this.pcestdttDTOs = pcestdttDTOs;
	}
	public List<SpestlabDTO> getSpestlabDTOs() {
		return spestlabDTOs;
	}
	public void setSpestlabDTOs(List<SpestlabDTO> spestlabDTOs) {
		this.spestlabDTOs = spestlabDTOs;
	}
	
	

}
