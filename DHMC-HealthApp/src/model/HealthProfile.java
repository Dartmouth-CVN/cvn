package model;

import java.util.ArrayList;

class HealthProfile {
	private ArrayList<HealthInfo> healthInfo;
	
	public HealthProfile() {
		this.setHealthInfo(new ArrayList<HealthInfo>());
	}

	public ArrayList<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(ArrayList<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}
	
	public void addHealthInfo(HealthInfo info) {
		healthInfo.add(info);
	}
}