package carsspecific.moos.moosmapping;

public class HelmBehaviourConstantDepth extends MOOSBehaviour {
	public HelmBehaviourConstantDepth(MOOSProcess parent, double depth) {
		super("BHV_ConstantDepth", parent);
		setProperty("name", "constdepth");
		//setProperty("pwt", 100);
		setProperty("condition", "(CONSTDEPTH==true)");
		setProperty("updates", "UP_DEPTH"); 
		setProperty("depth", depth);
		setProperty("duration", "no-time-limit");
		
	    setProperty("peakwidth", 3);
	    setProperty("basewidth", 2);
	    setProperty("summitdelta", 20);
	}
}
