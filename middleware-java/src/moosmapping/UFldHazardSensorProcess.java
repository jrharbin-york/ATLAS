package moosmapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import atlasdsl.*;

public class UFldHazardSensorProcess extends MOOSProcess {
	private List<EnvironmentalObject> objects = new ArrayList<EnvironmentalObject>(); 
	
	public UFldHazardSensorProcess(MOOSCommunity parent) {
		super("uFldHazardSensor", parent);
		setProperty("hazard_file", "hazards.txt");
		
		// TODO: factor these out into the DSL
		setProperty("sensor_config", "width=25, exp=4, pclass=0.80");
		setProperty("sensor_config", "width=50, exp=2, pclass=0.60");
		setProperty("sensor_config", "width=10, exp=6, pclass=0.93");
	}
	
	public void addObject(EnvironmentalObject eo) {
		objects.add(eo);
	}	
	
	public void generateCustomCode(MOOSFiles mf) throws IOException {
		FileWriter fw = mf.getOpenFile("hazards.txt");
		System.out.println("opening hazards.txt");
		for (EnvironmentalObject eo : objects) {
			System.out.println("DEBUG: adding line to hazards.txt");
			fw.write(eo.toString() + "\n");
		}
	}
}