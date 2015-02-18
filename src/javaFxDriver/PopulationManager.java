package javaFxDriver;

import java.util.HashMap;

public class PopulationManager {
	
	private HashMap<String, PopulationDriver> drivers = new HashMap<String, PopulationDriver>();
	private PopulationDriver activeDriver = null;
	
	public PopulationManager () {}
	
	public void addDriver (String name, PopulationDriver driver) {
		this.drivers.put(name, driver);
	}
	
	public void setActiveDriver (String name) {
		this.activeDriver = this.drivers.get(name);
	}
	
	public PopulationDriver getActiveDriver () {
		return this.activeDriver;
	}
	
	public void update (float elapsedTime) {
		if (this.activeDriver == null) return;
		this.activeDriver.update(elapsedTime);
	}
	
	//public List getDrivers () {}
	
	public void setC1 (float val) {
		if (this.activeDriver == null) return;
		this.activeDriver.getOptions().c1 = val;
	}
	
	public void setC2 (float val) {
		if (this.activeDriver == null) return;
		this.activeDriver.getOptions().c2 = val;
	}
	
	public void setSpeedLimit (float val) {
		if (this.activeDriver == null) return;
		this.activeDriver.getOptions().speedLimit = val;
	}

}
