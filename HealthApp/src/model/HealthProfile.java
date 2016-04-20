package model;

import java.util.LinkedList;

class HealthProfile {

	private LinkedList<HealthInfo> healthInfo;
	
	public HealthProfile() {
		this.healthInfo = new LinkedList<HealthInfo>();
	}
	
	public LinkedList<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(LinkedList<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}
	
	public void addHealthInfo(HealthInfo newInfo) {
		this.healthInfo.add(newInfo);
	}
	
	public void addHealthInfoList(LinkedList<HealthInfo> info) {
		for(int i = 0; i < info.size(); i++) {
			for(int j = 0; j< healthInfo.size(); j++) {
				if(info.get(i).equals(healthInfo.get(j))) {
					info.remove(i);
				}
			}
			healthInfo.add(info.get(i));
		}
	}
}
