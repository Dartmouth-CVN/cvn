package model;

import java.util.ArrayList;

class HealthProfile {
	private int ehrid;
	private ArrayList<Integer> height;
	private ArrayList<Double> weight;
	
	public HealthProfile() {
		this.ehrid = 0;
		this.height = new ArrayList<Integer>();
		this.weight = new ArrayList<Double>();
	}

	public int getEhrid() {
		return ehrid;
	}

	public ArrayList<Double> getWeight() {
		return weight;
	}

	public ArrayList<Integer> getHeight() {
		return height;
	}
	
	public void setEhrid(int ehrid) {
		this.ehrid = ehrid;
	}

	public void setHeight(int height) {
		this.height.add(height);
	}

	public void addWeight(double newWeight) {
		this.weight.add(newWeight);
	}
}